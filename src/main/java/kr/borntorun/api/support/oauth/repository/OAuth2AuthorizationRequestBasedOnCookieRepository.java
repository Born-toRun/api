package kr.borntorun.api.support.oauth.repository;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REDIRECT_URI;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import com.nimbusds.oauth2.sdk.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.borntorun.api.support.CookieSupport;

public class OAuth2AuthorizationRequestBasedOnCookieRepository
  implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
	private final static int cookieExpireSeconds = 180;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		return CookieSupport.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
		  .map(cookie -> CookieSupport.deserialize(cookie, OAuth2AuthorizationRequest.class))
		  .orElse(null);
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
	  HttpServletResponse response) {
		if (authorizationRequest == null) {
			CookieSupport.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
			CookieSupport.deleteCookie(request, response, REDIRECT_URI);
			CookieSupport.deleteCookie(request, response, REFRESH_TOKEN);
			return;
		}

		CookieSupport.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
		  CookieSupport.serialize(authorizationRequest), cookieExpireSeconds);
		String redirectUriAfterLogin = request.getParameter(REDIRECT_URI);
		if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
			CookieSupport.addCookie(response, REDIRECT_URI, redirectUriAfterLogin,
			  cookieExpireSeconds);
		}
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
	  HttpServletResponse response) {
		return this.loadAuthorizationRequest(request);
	}

	public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
		CookieSupport.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
		CookieSupport.deleteCookie(request, response, REDIRECT_URI);
		CookieSupport.deleteCookie(request, response, REFRESH_TOKEN);
	}
}
