package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원정보 수정")
public record ModifyUserRequest(
  @Schema(description = "프로필 사진 식별자")
  long profileImageId,

  @Schema(description = "인스타그램 아이디")
  String instagramId
) {
}