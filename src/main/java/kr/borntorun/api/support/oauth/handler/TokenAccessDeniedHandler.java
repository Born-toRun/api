package kr.borntorun.api.support.oauth.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

	private final HandlerExceptionResolver handlerExceptionResolver;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
	  AccessDeniedException accessDeniedException) {
		//response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
		handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
	}
}