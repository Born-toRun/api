package kr.borntorun.api.adapter.out.thirdparty;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import kr.borntorun.api.adapter.out.thirdparty.model.AuthSignInRequest;
import kr.borntorun.api.adapter.out.thirdparty.model.AuthSignInResponse;
import kr.borntorun.api.adapter.out.thirdparty.model.AuthTokenRequest;
import kr.borntorun.api.adapter.out.thirdparty.model.AuthTokenResponse;
import kr.borntorun.api.adapter.out.thirdparty.model.RefreshTokenResponse;
import kr.borntorun.api.config.properties.BornToRunAuthAdapterProperties;
import kr.borntorun.api.support.exception.ClientUnknownException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(BornToRunAuthAdapterProperties.class)
public class BornToRunAuthAdapter {

  private final BornToRunAuthAdapterProperties bornToRunAuthAdapterProperties;
  private final WebClient bornToRunAuthConnector;

  public AuthTokenResponse getToken(final String kakaoUserId) {
    final AuthTokenRequest request = new AuthTokenRequest(bornToRunAuthAdapterProperties.getGrantType());

    return bornToRunAuthConnector.post()
        .uri(uriBuilder -> uriBuilder
            .path(bornToRunAuthAdapterProperties.getAuthTokenPath())
            .build())
        .headers(headers -> headers.setBasicAuth(kakaoUserId, kakaoUserId))
        .headers(headers -> headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED))
        .body(request.getBody())
        .retrieve()
        .onStatus(status -> !status.is2xxSuccessful(), response -> response.bodyToMono(String.class).flatMap(body ->
            Mono.error(new ClientUnknownException(
                String.format("우리 토큰 요청 오류. statusCode: %d, response: %s",
                    response.statusCode().value(), body)
            )))
        )
        .bodyToMono(AuthTokenResponse.class)
        .block();
  }

  public AuthSignInResponse signIn(final AuthSignInRequest request) {
    return bornToRunAuthConnector.get()
        .uri(uriBuilder -> uriBuilder
            .path(bornToRunAuthAdapterProperties.getSignInPath())
            .queryParam("code", request.code())
            .build())
        .retrieve()
        .onStatus(status -> !status.is2xxSuccessful(), response ->
            Mono.error(new ClientUnknownException(
                String.format("로그인 오류. statusCode: %d",
                    response.statusCode().value())
            ))
        )
        .bodyToMono(AuthSignInResponse.class)
        .block();
  }

    public RefreshTokenResponse refreshToken(final String accessToken) {
        return bornToRunAuthConnector.post()
          .uri(uriBuilder -> uriBuilder
            .path(bornToRunAuthAdapterProperties.getRefreshTokenPath())
            .build())
          .headers(headers -> headers.setBearerAuth(accessToken))
          .retrieve()
          .onStatus(status -> !status.is2xxSuccessful(), response -> response.bodyToMono(String.class).flatMap(body ->
            Mono.error(new ClientUnknownException(
              String.format("우리 토큰 요청 오류. statusCode: %d, response: %s",
                response.statusCode().value(), body)
            )))
          )
          .bodyToMono(RefreshTokenResponse.class)
          .block();
    }
}
