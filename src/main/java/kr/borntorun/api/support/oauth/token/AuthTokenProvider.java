package kr.borntorun.api.support.oauth.token;

import static kr.borntorun.api.support.oauth.token.AuthToken.AUTHORITIES_KEY;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import io.jsonwebtoken.Claims;
import kr.borntorun.api.support.oauth.exception.TokenValidFailedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenProvider {

	private final SecretKey secretKey;

	public AuthTokenProvider(KeyGenerator keyGenerator) {
		this.secretKey = keyGenerator.generateKey();
	}

	public AuthToken createAuthToken(long id, Date expiry) {
		return new AuthToken(id, expiry, secretKey);
	}

	public AuthToken createAuthToken(long id, String userName, Long crewId, String role, Date expiry) {
		return new AuthToken(id, role, expiry, secretKey);
	}

	public AuthToken convertAuthToken(String token) {
		return new AuthToken(token, secretKey);
	}

	public Authentication getAuthentication(AuthToken authToken) {
		JwtDecoder jwtDecoder = NimbusJwtDecoder
		  .withSecretKey(secretKey)
		  .macAlgorithm(MacAlgorithm.HS512)
		  .build();

		if (authToken.isValidate()) {
			Claims claims = authToken.getTokenClaims();
			Collection<? extends GrantedAuthority> authorities =
			  Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY).toString()})
				.map(SimpleGrantedAuthority::new)
				.toList();

			log.debug("claims subject := [{}]", claims.getSubject());

			final Jwt jwt = jwtDecoder.decode(authToken.getToken());
			return new JwtAuthenticationToken(jwt, authorities);
		} else {
			throw new TokenValidFailedException();
		}
	}
}
