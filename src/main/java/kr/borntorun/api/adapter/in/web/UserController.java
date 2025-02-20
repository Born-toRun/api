package kr.borntorun.api.adapter.in.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.ModifyUserRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyUserResponse;
import kr.borntorun.api.adapter.in.web.payload.SignUpRequest;
import kr.borntorun.api.adapter.in.web.payload.SignUpResponse;
import kr.borntorun.api.adapter.in.web.payload.UserDetailResponse;
import kr.borntorun.api.adapter.in.web.proxy.UserProxy;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.RefreshTokenResult;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "사용자", description = "회원 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserConverter userConverter;

  private final UserProxy userProxy;

  @Operation(summary = "토큰 리프레시", description = "access 토큰이 만료되면 refresh 토큰으로 재생성합니다.")
  @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> signIn(@RequestBody @Valid HttpServletRequest request, HttpServletRequest servletRequest) {
    String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    final RefreshTokenResult refreshToken = userProxy.refreshToken(accessToken);
    servletRequest.getSession()
      .setAttribute("jwt", refreshToken.accessToken());

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "회원가입", description = "회원가입 합니다.")
  @PutMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SignUpResponse> signUp(@AuthUser TokenDetail my, @RequestBody @Valid SignUpRequest request) {
    final String createdUserName = userProxy.signUp(my, request);
    return ResponseEntity.ok(new SignUpResponse(createdUserName));
  }

  @Operation(summary = "회원탈퇴", description = "회원탈퇴 합니다.")
  @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public void removeAccount(@AuthUser TokenDetail my) {
    userProxy.remove(my.getId());
  }

  @Operation(summary = "나의 회원 정보 조회", description = "나의 회원정보를 조회합니다.")
  @GetMapping(value = "/my", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDetailResponse> detail(@AuthUser TokenDetail my) {
    final BornToRunUser user = userProxy.search(my);
    return ResponseEntity.ok(
        userConverter.toUserDetailResponse(user));
  }

  @Operation(summary = "회원 정보 조회", description = "회원정보를 조회합니다.")
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDetailResponse> detail(@PathVariable long userId) {
    final BornToRunUser user = userProxy.search(userId);
    UserDetailResponse userDetailResponse = userConverter.toUserDetailResponse(user);
    return ResponseEntity.ok(userDetailResponse);
  }

  @Operation(summary = "회원정보 수정", description = "회원 정보를 수정합니다.")
  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ModifyUserResponse> modify(@AuthUser TokenDetail my, @Valid @RequestBody ModifyUserRequest request) {
    BornToRunUser modifiedUser = userProxy.modify(my, request);
    ModifyUserResponse modifyUserResponse = userConverter.toModifyUserResponse(modifiedUser);
    return ResponseEntity.ok(modifyUserResponse);
  }
}
