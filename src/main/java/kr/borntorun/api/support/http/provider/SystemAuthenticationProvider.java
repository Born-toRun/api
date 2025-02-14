package kr.borntorun.api.support.http.provider;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SystemAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String jwt = (String) authentication.getPrincipal();

    if (jwt == null) {
      return null;
    }
    return new PreAuthenticatedAuthenticationToken(jwt, null, Collections.emptyList());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
  }
}