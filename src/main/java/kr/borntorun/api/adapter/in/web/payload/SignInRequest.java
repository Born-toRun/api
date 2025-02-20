package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "카카오 로그인")
public record SignInRequest(
  @Schema(description = "카카오로부터 받은 인가코드")
  @NotNull
  String kakaoAuthCode
) {
}