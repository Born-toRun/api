package kr.borntorun.api.adapter.in.web.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 정보 조회")
public record UserDetailResponse(
    @Schema(description = "회원 식별자")
    long userId,

    @Schema(description = "유저명")
    String userName,

    @Schema(description = "소속 크루명")
    String crewName,

    @Schema(description = "프로필 이미지")
    String profileImageUri,

    @Schema(description = "관리자 여부")
    Boolean isAdmin,

    @Schema(description = "운영진 여부")
    Boolean isManager,

    @Schema(description = "받은 경고 개수")
    int yellowCardQty,

    @JsonIgnore
    String instagramId,

    @Schema(description = "인스타그램 계정 공개 여부")
    Boolean isInstagramIdPublic
) {
  @Schema(description = "인스타그램 계정 uri")
  public String getInstagramUri() {
    return "https://www.instagram.com/" + instagramId;
  }
}