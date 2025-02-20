package kr.borntorun.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.borntorun.api.support.oauth.token.AuthTokenProvider;

@Configuration
public class JwtConfig {
	@Value("${jwt.secret}")
	private String secret;

	@Bean
	public AuthTokenProvider jwtProvider() {
		return new AuthTokenProvider(secret);
	}
}
