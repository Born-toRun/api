package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.LoginResult;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.SignInCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;

public interface UserPort {

  String getKakaoAuthCodeUri();
  LoginResult signIn(final SignInCommand command);
  String signUp(final SignUpCommand command);
  void remove(final int userId);
  BornToRunUser search(final int userId);
  String modify(final ModifyUserCommand command);
}
