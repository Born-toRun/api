package kr.borntorun.api.support;

import java.util.Optional;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import kr.borntorun.api.domain.constant.RoleType;
import kr.borntorun.api.support.oauth.token.AuthToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class TokenDetail {

	private long id;
	private String userName;
	private String authority;
	private Long crewId;
	private Boolean isAdmin;
	private Boolean isManager;

	public TokenDetail(JwtAuthenticationToken token) {
		final Jwt jwt = token.getToken();

		this.id = Long.parseLong(jwt.getSubject());
		this.userName = jwt.getClaimAsString(AuthToken.USER_NAME_KEY);
		this.authority = jwt.getClaimAsString(AuthToken.AUTHORITIES_KEY);
		this.crewId = Optional.ofNullable(jwt.getClaimAsString(AuthToken.CREW_ID_KEY))
		  .map(Long::valueOf)
		  .orElse(null);
		this.isAdmin = this.authority.equals(RoleType.ADMIN.getCode());
		this.isManager = this.authority.equals(RoleType.MANAGER.getCode());
	}

	public static TokenDetail defaultUser() {
		return TokenDetail.builder().id(-1).build();
	}

	public boolean isLogin() {
		return id > 0;
	}
}
