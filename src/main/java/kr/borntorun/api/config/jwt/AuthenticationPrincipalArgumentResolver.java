package kr.borntorun.api.config.jwt;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;

public final class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(AuthUser.class) != null;
  }

  @Override
  public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
      return TokenDetail.defaultUser();
    }

    return new TokenDetail(jwtAuthenticationToken);
  }
}
