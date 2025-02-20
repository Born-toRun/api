package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.CreateGuestCommand;
import kr.borntorun.api.domain.port.model.LoginResult;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.SignInCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;

public interface UserPort {

  LoginResult signIn(final SignInCommand command);
  String signUp(final SignUpCommand command);
  String refreshToken(final String accessToken);
  void remove(final long userId);
  BornToRunUser searchById(final long userId);
  BornToRunUser searchBySocialId(final String socialId);
  BornToRunUser modify(final ModifyUserCommand command);
  BornToRunUser create(CreateGuestCommand command);
}
