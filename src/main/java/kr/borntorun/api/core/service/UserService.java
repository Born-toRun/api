package kr.borntorun.api.core.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.adapter.out.thirdparty.BornToRunAuthAdapter;
import kr.borntorun.api.adapter.out.thirdparty.model.AuthSignInResponse;
import kr.borntorun.api.adapter.out.thirdparty.model.AuthTokenResponse;
import kr.borntorun.api.config.properties.KakaoProperties;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.port.UserPort;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.LoginResult;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.SignInCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;
import kr.borntorun.api.infrastructure.UserGateway;
import lombok.RequiredArgsConstructor;

@EnableConfigurationProperties({KakaoProperties.class})
@RequiredArgsConstructor
@Service
public class UserService implements UserPort {

  private final BornToRunAuthAdapter borntorunAuthAdapter;
  private final KakaoProperties kakaoProperties;
  private final UserGateway userGateway;

  @Override
  public String getKakaoAuthCodeUri() {
    return kakaoProperties.getAuthCodeUri();
  }

  @Override
  public LoginResult signIn(final SignInCommand command) {
    final AuthSignInResponse authSignInResponse = borntorunAuthAdapter.signIn(UserConverter.INSTANCE.toAuthSignUpRequest(command));
    final AuthTokenResponse authTokenResponse = borntorunAuthAdapter.getToken(authSignInResponse.kakaoId());

    return new LoginResult(authTokenResponse.accessToken(), authSignInResponse.isMember());
  }

  @Transactional
  @Override
  public String signUp(final SignUpCommand command) {
    return userGateway.modify(UserConverter.INSTANCE.toSignUpUserQuery(command));
  }

  @Transactional
  @Override
  public void remove(final int userId) {
    // TODO
  }

  @Transactional(readOnly = true)
  @Override
  public BornToRunUser search(final int userId) {
    return UserConverter.INSTANCE.toBornToRunUser(userGateway.search(userId));
  }

  @Transactional
  @Override
  public String modify(final ModifyUserCommand command) {
    return userGateway.modify(UserConverter.INSTANCE.toModifyUserQuery(command));
  }
}
