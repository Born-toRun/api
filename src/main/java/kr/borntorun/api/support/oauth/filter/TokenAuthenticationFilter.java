package kr.borntorun.api.support.oauth.filter;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.borntorun.api.support.HeaderSupport;
import kr.borntorun.api.support.oauth.token.AuthToken;
import kr.borntorun.api.support.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private final AuthTokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(
	  @NotNull HttpServletRequest request,
	  @NotNull HttpServletResponse response,
	  @NotNull FilterChain filterChain) throws ServletException, IOException {

		String token = HeaderSupport.getAccessToken(request);

		try {
			if (token != null) {
				AuthToken authToken = tokenProvider.convertAuthToken(token);
				authToken.validate();

				Authentication authentication = tokenProvider.getAuthentication(authToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				// 미회원 접근
			}
		} finally {
			filterChain.doFilter(request, response);
		}
	}

}
