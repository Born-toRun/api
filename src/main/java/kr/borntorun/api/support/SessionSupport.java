package kr.borntorun.api.support;

import jakarta.servlet.http.HttpServletRequest;
import kr.borntorun.api.domain.constant.TokenType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionSupport {

	private final static String TOKEN_PREFIX = "Bearer ";

	public static String getAccessToken(HttpServletRequest request) {
		String value = String.valueOf(request.getSession().getAttribute(TokenType.ACCESS_TOKEN.name()));

		if (value == null || value.equals("null")) {
			return null;
		}

		if (value.startsWith(TOKEN_PREFIX)) {
			return value.substring(TOKEN_PREFIX.length());
		} else {
			return value;
		}
	}

	public static void setAccessToken(HttpServletRequest request, String token) {
		setToken(request, TokenType.ACCESS_TOKEN.name(), token);
	}

	public static void setRefreshToken(HttpServletRequest request, String token) {
		setToken(request, TokenType.REFRESH_TOKEN.name(), token);
	}

	private static void setToken(HttpServletRequest request, String tokenName, String token) {
		if (token == null) {
			log.warn("Token is null!");
			return;
		}
		request.getSession().setAttribute(tokenName, token);
	}
}
