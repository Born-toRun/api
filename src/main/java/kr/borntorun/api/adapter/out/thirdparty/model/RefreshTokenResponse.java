package kr.borntorun.api.adapter.out.thirdparty.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenResponse(@JsonProperty("access_token") String accessToken) {
}
