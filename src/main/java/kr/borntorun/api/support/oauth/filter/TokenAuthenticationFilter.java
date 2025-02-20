package kr.borntorun.api.support.oauth.filter;

import java.io.IOException;

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
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String tokenStr = HeaderSupport.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        try {
            token.validate();

            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

}
