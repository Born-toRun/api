package kr.borntorun.api.config.jwt;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

	private final Converter<JwtAuthenticationToken, TokenDetail> customJwtAuthenticationConverter;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthenticationPrincipal.class) &&
		  parameter.getParameterType().equals(TokenDetail.class);
	}

	@Override
	public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer,
	  @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		try {
			JwtAuthenticationToken authentication = (JwtAuthenticationToken)SecurityContextHolder.getContext()
			  .getAuthentication();
			return customJwtAuthenticationConverter.convert(authentication);
		} catch (Exception e) {
			return null;
		}
	}
}