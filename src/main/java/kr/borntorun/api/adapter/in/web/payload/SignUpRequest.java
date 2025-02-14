package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Schema(description = "회원가입")
public record SignUpRequest(
    @NotNull
    @Schema(description = "유저명")
    String userName,

    @NotNull
    @Schema(description = "크루(소속 팀) 식별자")
    int crewId,

    @Schema(description = "인스타그램 아이디")
    String instagramId
) {
}