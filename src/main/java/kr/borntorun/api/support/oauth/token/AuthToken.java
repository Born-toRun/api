package kr.borntorun.api.support.oauth.token;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import kr.borntorun.api.support.exception.InvalidTokenException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

	public static final String AUTHORITIES_KEY = "role";
	public static final String USER_NAME_KEY = "userName";
	public static final String CREW_ID_KEY = "crewId";

	@Getter
	private final String token;
	private final SecretKey key;

	AuthToken(long id, Date expiry, SecretKey key) {
		this.key = key;
		this.token = createAuthToken(id, expiry);
	}

	AuthToken(long id, String userName, Long crewId, String role, Date expiry, SecretKey key) {
		this.key = key;
		this.token = createAuthToken(id, userName, crewId, role, expiry);
	}

	private String createAuthToken(long id, Date expiry) {
		return Jwts.builder()
		  .subject(String.valueOf(id))
		  .signWith(key, Jwts.SIG.HS512)
		  .expiration(expiry)
		  .compact();
	}

	private String createAuthToken(long id, String userName, Long crewId, String role, Date expiry) {
		return Jwts.builder()
		  .subject(String.valueOf(id))
		  .claim(AUTHORITIES_KEY, role)
		  .claim(USER_NAME_KEY, userName)
		  .claim(CREW_ID_KEY, crewId)
		  .signWith(key, Jwts.SIG.HS512)
		  .expiration(expiry)
		  .compact();
	}

	public boolean isValidate() {
		try {
			validate();
		} catch (InvalidTokenException e) {
			return false;
		}

		return true;
	}

	public void validate() {
		try {
			Jwts.parser()
			  .verifyWith(key)
			  .build()
			  .parseSignedClaims(token)
			  .getPayload();
		} catch (SecurityException | SignatureException e) {
			throw new InvalidTokenException("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			throw new InvalidTokenException("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			log.warn("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			throw new InvalidTokenException("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			throw new InvalidTokenException("JWT token compact of handler are invalid.");
		}
	}

	public Claims getTokenClaims() {
		try {
			return Jwts.parser()
			  .verifyWith(key)
			  .build()
			  .parseSignedClaims(token)
			  .getPayload();
		} catch (SecurityException e) {
			log.warn("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			log.warn("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			log.warn("Expired JWT token.");
		} catch (UnsupportedJwtException e) {
			log.warn("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			log.warn("JWT token compact of handler are invalid.");
		}
		return null;
	}
}
