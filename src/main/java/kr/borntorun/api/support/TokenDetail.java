package kr.borntorun.api.support;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class TokenDetail {

  private int id;
  private String userName;
  private List<String> authorities;

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
  }

  public boolean isLogin() {
    return id > 0;
  }
}
