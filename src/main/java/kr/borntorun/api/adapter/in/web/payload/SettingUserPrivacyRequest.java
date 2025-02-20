package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "회원정보 노출 동의 여부")
public record SettingUserPrivacyRequest(
  @Schema(description = "인스타그램 아이디 노출 동의 여부") @NotNull Boolean isInstagramIdPublic) {
}