package kr.borntorun.api.support.oauth.handler;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REDIRECT_URI;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.borntorun.api.support.CookieSupport;
import kr.borntorun.api.support.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	  AuthenticationException exception) throws IOException {
		String targetUrl = CookieSupport.getCookie(request, REDIRECT_URI)
		  .map(Cookie::getValue)
		  .orElse(("/"));

		targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
		  .queryParam("error", exception.getLocalizedMessage())
		  .build().toUriString();

		authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

		// TODO: 인증 실패시 회원가입으로 이동하는 건 말이 안됨. 홈으로 이동?
		getRedirectStrategy().sendRedirect(request, response, targetUrl);

		log.info("Authentication failed! {}", exception.getMessage());
	}
}
