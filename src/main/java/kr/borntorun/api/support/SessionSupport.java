package kr.borntorun.api.support;

import jakarta.servlet.http.HttpServletRequest;
import kr.borntorun.api.domain.constant.TokenType;

public class HeaderSupport {

	private final static String TOKEN_PREFIX = "Bearer ";

	public static String getAccessToken(HttpServletRequest request) {
		String headerValue = String.valueOf(request.getSession().getAttribute(TokenType.ACCESS_TOKEN.name()));

		if (headerValue == null) {
			return null;
		}

		if (headerValue.startsWith(TOKEN_PREFIX)) {
			return headerValue.substring(TOKEN_PREFIX.length());
		}

		return null;
	}
}
