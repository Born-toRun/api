package kr.borntorun.api.adapter.in.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import kr.borntorun.api.adapter.in.web.payload.SignInRequest;
import kr.borntorun.api.adapter.in.web.payload.SignInResponse;
import kr.borntorun.api.adapter.in.web.payload.SignUpRequest;
import kr.borntorun.api.adapter.in.web.payload.SignUpResponse;
import kr.borntorun.api.adapter.in.web.payload.UserDetailResponse;
import kr.borntorun.api.adapter.in.web.proxy.UserProxy;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.LoginResult;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "사용자", description = "회원 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserProxy userProxy;

  @Value("${cors.origin}")
  private String origin;

  @Operation(summary = "카카오 인가코드 요청", description = "카카오 인가코드 요청 리다이렉트")
  @GetMapping(value = "/kakao/auth-code")
  public ResponseEntity<Void> kakaoAuthCode() {
    return ResponseEntity.status(HttpStatus.FOUND.value())
        .location(URI.create(userProxy.getKakaoAuthCodeUri()))
        .headers(header -> {
          header.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
          header.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.GET.name());
        })
        .build();
  }

  @Operation(summary = "카카오 로그인", description = "카카오에서 발행한 인가코드를 사용하여 카카오 로그인을 합니다.")
  @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest request, HttpServletRequest servletRequest) {
    final LoginResult loginResult = userProxy.signIn(request);
    servletRequest.getSession()
      .setAttribute("jwt", loginResult.accessToken());

    return ResponseEntity.ok(new SignInResponse(loginResult.isMember()));
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
        UserConverter.INSTANCE.toUserDetailResponse(user));
  }

  @Operation(summary = "회원 정보 조회", description = "회원정보를 조회합니다.")
  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDetailResponse> detail(@PathVariable int userId) {
    final BornToRunUser user = userProxy.search(userId);
    UserDetailResponse userDetailResponse = UserConverter.INSTANCE.toUserDetailResponse(user);
    return ResponseEntity.ok(userDetailResponse);
  }

  @Operation(summary = "회원정보 수정", description = "회원 정보를 수정합니다.")
  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ModifyUserResponse> modify(@AuthUser TokenDetail my, @Valid @RequestBody ModifyUserRequest request) {
    BornToRunUser modifiedUser = userProxy.modify(my, request);
    ModifyUserResponse modifyUserResponse = UserConverter.INSTANCE.toModifyUserResponse(modifiedUser);
    return ResponseEntity.ok(modifyUserResponse);
  }
}
