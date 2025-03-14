package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.CreateUserCommand;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;

public interface UserPort {

	String signUp(final SignUpCommand command);

	String getRefreshToken(final String accessToken);

	void remove(final long userId);

	BornToRunUser searchById(final long userId);

	BornToRunUser searchBySocialId(final String socialId);

	BornToRunUser modify(final ModifyUserCommand command);

	BornToRunUser createAndFlush(final CreateUserCommand command);

	boolean exists(final String socialId);
}
