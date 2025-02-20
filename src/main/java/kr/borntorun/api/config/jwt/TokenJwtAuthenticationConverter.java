package kr.borntorun.api.config.jwt;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import kr.borntorun.api.support.TokenDetail;

@Component
public class TokenJwtAuthenticationConverter implements Converter<JwtAuthenticationToken, TokenDetail> {

	@Override
	public TokenDetail convert(@NotNull JwtAuthenticationToken jwt) {
		return new TokenDetail(jwt);
	}
}