package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.ModifyUserRequest;
import kr.borntorun.api.adapter.in.web.payload.SignInRequest;
import kr.borntorun.api.adapter.in.web.payload.SignUpRequest;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.core.service.UserService;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.LoginResult;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserProxy {

  private final UserService userService;

  @Cacheable(key = "'kakaoAuthCode'")
  public String getKakaoAuthCodeUri() {
    return userService.getKakaoAuthCodeUri();
  }

  public LoginResult signIn(final SignInRequest request) {
    return userService.signIn(UserConverter.INSTANCE.toSignInCommand(request));
  }

  @CacheEvict(allEntries = true)
  public String signUp(final TokenDetail my, final SignUpRequest request) {
    return userService.signUp(UserConverter.INSTANCE.toSignUpCommand(request, my.getId()));
  }

  @CacheEvict(allEntries = true)
  public void remove(final int myUserId) {
    userService.remove(myUserId);
  }

  @Cacheable(key = "'search: ' + #my.hashCode()")
  public BornToRunUser search(final TokenDetail my) {
    return userService.search(my.getId());
  }

  @Cacheable(key = "'search: ' + #userId")
  public BornToRunUser search(final int userId) {
    return userService.search(userId);
  }

  @CacheEvict(allEntries = true)
  public String modify(final TokenDetail my, final ModifyUserRequest request) {
    return userService.modify(UserConverter.INSTANCE.toModifyUserCommand(request, my.getId()));
  }
}
