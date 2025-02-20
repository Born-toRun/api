package kr.borntorun.api.support;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import kr.borntorun.api.domain.constant.RoleType;
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
	private List<String> authorities;
	private Long crewId;
	private Boolean isAdmin;
	private Boolean isManager;

	public static TokenDetail defaultUser() {
		return TokenDetail.builder().id(-1).build();
	}

	public TokenDetail(JwtAuthenticationToken token) {
		final Jwt jwt = token.getToken();

		this.id = Integer.parseInt(jwt.getClaimAsString("id"));
		this.userName = jwt.getClaimAsString("userName");
		this.authorities = token.getAuthorities().stream()
		  .map(GrantedAuthority::getAuthority)
		  .collect(Collectors.toList());
		this.crewId = jwt.getClaimAsString("crewId") == null ? null : Long.valueOf(jwt.getClaimAsString("crewId"));
		this.isAdmin = this.authorities.contains(RoleType.ADMIN.name());
		this.isManager = this.authorities.contains(RoleType.MANAGER.name());
	}

	public boolean isLogin() {
		return id > 0;
	}
}
