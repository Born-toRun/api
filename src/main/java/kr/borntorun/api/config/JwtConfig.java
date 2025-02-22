package kr.borntorun.api.config;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.borntorun.api.support.oauth.token.AuthTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class JwtConfig {

	private final String algorithm = "HmacSHA256";

	private KeyGenerator keyGenerator;

	@Bean
	public AuthTokenProvider jwtProvider() {
		try {
			keyGenerator = KeyGenerator.getInstance(algorithm);
			keyGenerator.init(512);

			return new AuthTokenProvider(keyGenerator);
		} catch (NoSuchAlgorithmException e) {
			log.error("{} algorithm이 제공되지 않습니다.", algorithm);
			throw new RuntimeException(e);
		}
	}
}
