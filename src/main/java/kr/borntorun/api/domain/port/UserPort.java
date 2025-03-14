package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.CreateUserCommand;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;

public interface UserPort {

	String signUp(SignUpCommand command);

	String getRefreshToken(String accessToken);

	void remove(long userId);

	BornToRunUser searchById(long userId);

	BornToRunUser searchBySocialId(String socialId);

	BornToRunUser modify(ModifyUserCommand command);

	BornToRunUser createAndFlush(CreateUserCommand command);

	boolean exists(String socialId);
}
