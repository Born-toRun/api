package kr.borntorun.api.support.oauth.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.domain.constant.RoleType;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {
	private final long userId;
	private final String name;
	private final String password;
	private final ProviderType providerType;
	private final RoleType roleType;
	private final Collection<GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getUsername() {
		return String.valueOf(userId);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getClaims() {
		return null;
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return null;
	}

	@Override
	public OidcIdToken getIdToken() {
		return null;
	}

	public static UserPrincipal create(BornToRunUser user) {
		return new UserPrincipal(
		  user.userId(),
		  user.userName(),
		  user.socialId(),
		  user.providerType(),
		  user.roleType(),
		  Collections.singletonList(new SimpleGrantedAuthority(user.roleType().getCode()))
		);
	}

	public static UserPrincipal create(BornToRunUser user, Map<String, Object> attributes) {
		UserPrincipal userPrincipal = create(user);
		userPrincipal.setAttributes(attributes);

		return userPrincipal;
	}
}
