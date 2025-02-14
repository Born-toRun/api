package kr.borntorun.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@ConfigurationProperties("adapter.auth.kakao")
public class KakaoProperties {

  private final String authRootUrl;
  private final String authCodePath;
  private final String clientId;
  private final String redirectUri;
  private final String responseType;
  private final String scope;

  public String getAuthCodeUri() {

    return authRootUrl
        + authCodePath
        + "?"
        + "client_id="
        + clientId
        + "&redirect_uri="
        + redirectUri
        + "&response_type="
        + responseType
        + "&scope="
        + scope;
  }
}