package kr.borntorun.api.support;

import java.util.List;
import java.util.Optional;
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

	public TokenDetail(JwtAuthenticationToken token) {
		final Jwt jwt = token.getToken();

		this.id = Long.parseLong(jwt.getSubject());
		this.userName = jwt.getClaimAsString("userName");
		this.authorities = token.getAuthorities().stream()
		  .map(GrantedAuthority::getAuthority)
		  .collect(Collectors.toList());
		this.crewId = Optional.ofNullable(jwt.getClaimAsString("crewId"))
		  .map(Long::valueOf)
		  .orElse(null);
		this.isAdmin = this.authorities.contains(RoleType.ADMIN.getCode());
		this.isManager = this.authorities.contains(RoleType.MANAGER.getCode());
	}

	public static TokenDetail defaultUser() {
		return TokenDetail.builder().id(-1).build();
	}

	public boolean isLogin() {
		return id > 0;
	}
}
