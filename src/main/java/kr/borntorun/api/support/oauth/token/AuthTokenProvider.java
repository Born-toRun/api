package kr.borntorun.api.support.oauth.token;

import static kr.borntorun.api.support.oauth.token.AuthToken.AUTHORITIES_KEY;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.borntorun.api.support.oauth.exception.TokenValidFailedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenProvider {

	private final SecretKey secretKey;

	public AuthTokenProvider(String secret) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
	}

	public AuthToken createAuthToken(long id, Date expiry) {
		return new AuthToken(id, expiry, secretKey);
	}

	public AuthToken createAuthToken(long id, String role, Date expiry) {
		return new AuthToken(id, role, expiry, secretKey);
	}

	public AuthToken convertAuthToken(String token) {
		return new AuthToken(token, secretKey);
	}

	public Authentication getAuthentication(AuthToken authToken) {

		if (authToken.isValidate()) {
			Claims claims = authToken.getTokenClaims();
			Collection<? extends GrantedAuthority> authorities =
			  Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY).toString()})
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

			log.debug("claims subject := [{}]", claims.getSubject());
			User principal = new User(claims.getSubject(), "", authorities);

			return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
		} else {
			throw new TokenValidFailedException();
		}
	}
}
