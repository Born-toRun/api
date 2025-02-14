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
  private int crewId;
  private String birthday;
  private String gender;
  private String instagramId;
  private String crewName;
  private String crewSNS;
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
    this.crewId = Integer.parseInt(jwt.getClaimAsString("crewId"));
    this.birthday = jwt.getClaimAsString("birthday");
    this.gender = jwt.getClaimAsString("gender");
    this.instagramId = jwt.getClaimAsString("instagramId");
    this.crewName = jwt.getClaimAsString("crewName");
    this.crewSNS = jwt.getClaimAsString("crewSNS");
    this.isAdmin = jwt.getClaimAsBoolean("isAdmin");
    this.isManager = jwt.getClaimAsBoolean("isManager");
  }

  public boolean isLogin() {
    return id > 0;
  }
}
