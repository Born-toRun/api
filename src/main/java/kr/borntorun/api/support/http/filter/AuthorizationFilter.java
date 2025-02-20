package kr.borntorun.api.support.http.filter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.borntorun.api.support.http.provider.SystemAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class AuthorizationFilter extends AbstractPreAuthenticatedProcessingFilter {

  private final JwtDecoder jwtDecoder;
  private Object jwt;

  public AuthorizationFilter(final JwtDecoder jwtDecoder, final SystemAuthenticationProvider systemAuthenticationProvider) {
    this.jwtDecoder = jwtDecoder;
    super.setCheckForPrincipalChanges(true);
    super.setAuthenticationManager(new ProviderManager(systemAuthenticationProvider));
  }

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    jwt = Objects.isNull(jwt) ? request.getSession().getAttribute("jwt") : jwt;
    return jwt;
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return null;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, authResult);
    final String token = String.valueOf(getPreAuthenticatedPrincipal(request));

    if (token != null) {
      try {
        final Jwt jwt = jwtDecoder.decode(token);
        final Object authorities = jwt.getClaims().get("authorities");
        if (authorities instanceof List) {
          SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt, ((List<?>) authorities).stream()
              .map(authority -> new SimpleGrantedAuthority((String) authority))
              .toList()));
        }
      } catch (JwtException e) {
		  log.error("invalid jwt: [{}]: ", token, e);
        super.unsuccessfulAuthentication(request, response, null);
      }
    } else {
      log.debug("token not found!!!");
    }
  }
}
