package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "로그인")
@Builder
public record SignInResponse(
    @Schema(description = "기회원 여부")
    Boolean isMember
) {
}