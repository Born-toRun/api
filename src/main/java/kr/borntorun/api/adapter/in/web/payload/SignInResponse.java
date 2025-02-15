package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인")
public record SignInResponse(
    @Schema(description = "기회원 여부")
    Boolean isMember
) {
}