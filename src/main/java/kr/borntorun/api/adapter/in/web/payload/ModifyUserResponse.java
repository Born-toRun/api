package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입")
public record ModifyUserResponse(
    @Schema(description = "유저 명")
    String userName,

    @Schema(description = "크루 명")
    String crewName,

    @Schema(description = "인스타그램 아이디")
    String instagramId,

    @Schema(description = "프로필 이미지 uri")
    String profileImageUri
) {
}