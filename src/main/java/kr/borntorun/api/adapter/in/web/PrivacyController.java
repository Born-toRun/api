package kr.borntorun.api.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.SearchUserPrivacyResponse;
import kr.borntorun.api.adapter.in.web.payload.SettingUserPrivacyRequest;
import kr.borntorun.api.adapter.in.web.proxy.PrivacyProxy;
import kr.borntorun.api.core.converter.PrivacyConverter;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "정보 노출 동의", description = "정보 노출 동의/비동의 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/privacy")
public class PrivacyController {

  private final PrivacyProxy privacyProxy;

  @Operation(summary = "유저 정보 노출 동의 여부 선택", description = "정보 노출을 동의 혹은 비동의합니다.")
  @RequestMapping(value = "/user", method= RequestMethod.PUT, produces="application/json;charset=UTF-8")
  public void settingUserPrivacy(@AuthUser TokenDetail my, @RequestBody @Valid SettingUserPrivacyRequest request) {
    privacyProxy.modifyUserPrivacy(request, my.getId());
  }

  @Operation(summary = "유저 정보 노출 동의 여부 조회", description = "정보 노출을 동의 여부를 조회합니다.")
  @RequestMapping(value = "/user", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
  public ResponseEntity<SearchUserPrivacyResponse> getUserPrivacy(@AuthUser TokenDetail my) {
    return ResponseEntity.ok(PrivacyConverter.INSTANCE.toSearchUserPrivacyResponse(privacyProxy.searchUserPrivacy(my.getId())));
  }
}