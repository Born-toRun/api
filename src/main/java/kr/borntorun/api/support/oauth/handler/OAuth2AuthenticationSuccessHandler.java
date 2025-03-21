package kr.borntorun.api.support.oauth.handler;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REDIRECT_URI;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.borntorun.api.config.properties.AppProperties;
import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.domain.constant.RoleType;
import kr.borntorun.api.domain.entity.UserRefreshTokenEntity;
import kr.borntorun.api.domain.port.UserPort;
import kr.borntorun.api.domain.port.UserRefreshTokenPort;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.CreateRefreshTokenCommand;
import kr.borntorun.api.domain.port.model.CreateUserCommand;
import kr.borntorun.api.support.CookieSupport;
import kr.borntorun.api.support.exception.InternalServerException;
import kr.borntorun.api.support.oauth.info.OAuth2UserInfo;
import kr.borntorun.api.support.oauth.info.OAuth2UserInfoFactory;
import kr.borntorun.api.support.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import kr.borntorun.api.support.oauth.token.AuthToken;
import kr.borntorun.api.support.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final AuthTokenProvider tokenProvider;
	private final AppProperties appProperties;
	private final UserPort userPort;
	private final UserRefreshTokenPort userRefreshTokenPort;
	private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	  Authentication authentication) throws IOException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);

		log.info("Authentication succeed!");
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
	  Authentication authentication) {
		Optional<String> redirectUri = CookieSupport.getCookie(request, REDIRECT_URI)
		  .map(Cookie::getValue);

		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new InternalServerException(
			  "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken)authentication;
		ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

		OidcUser user = ((OidcUser)authentication.getPrincipal());
		OAuth2UserInfo socialUser = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

		CreateUserCommand createUserCommand = new CreateUserCommand(socialUser.getId(), providerType, RoleType.GUEST);
		BornToRunUser bornToRunUser;
		if (!userPort.exists(socialUser.getId())) {
			bornToRunUser = userPort.createAndFlush(createUserCommand);
		} else {
			bornToRunUser = userPort.searchBySocialId(socialUser.getId());
		}

		Date now = new Date();
		AuthToken accessToken = tokenProvider.createAuthToken(
		  bornToRunUser.userId(),
		  bornToRunUser.userName(),
		  bornToRunUser.crewId(),
		  bornToRunUser.roleType().getCode(),
		  new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
		);

		// refresh 토큰 설정
		long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

		AuthToken refreshToken = tokenProvider.createAuthToken(
		  bornToRunUser.userId(),
		  new Date(now.getTime() + refreshTokenExpiry)
		);

		// DB 저장
		UserRefreshTokenEntity userRefreshTokenEntity = userRefreshTokenPort.searchByUserId(bornToRunUser.userId());
		if (userRefreshTokenEntity != null) {
			userRefreshTokenEntity.setRefreshToken(refreshToken.getToken());
		} else {
			CreateRefreshTokenCommand createRefreshTokenCommand = new CreateRefreshTokenCommand(bornToRunUser.userId(),
			  refreshToken.getToken());
			userRefreshTokenPort.createAndFlush(createRefreshTokenCommand);
		}

		int cookieMaxAge = (int)refreshTokenExpiry / 60;

		CookieSupport.deleteCookie(request, response, REFRESH_TOKEN);
		CookieSupport.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

		return UriComponentsBuilder.fromUriString(targetUrl)
		  .queryParam("isMember", bornToRunUser.crewId() != null)
		  .queryParam("accessToken", accessToken.getToken())
		  .build().toUriString();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
		if (authorities == null) {
			return false;
		}

		for (GrantedAuthority grantedAuthority : authorities) {
			if (authority.equals(grantedAuthority.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);

		return appProperties.getOauth2().getAuthorizedRedirectUris()
		  .stream()
		  .anyMatch(authorizedRedirectUri -> {
			  // Only validate host and port. Let the clients use different paths if they want to
			  URI authorizedURI = URI.create(authorizedRedirectUri);
			  return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
				&& authorizedURI.getPort() == clientRedirectUri.getPort();
		  });
	}
}
