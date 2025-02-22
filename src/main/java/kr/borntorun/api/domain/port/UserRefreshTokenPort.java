package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.entity.UserRefreshTokenEntity;
import kr.borntorun.api.domain.port.model.CreateRefreshTokenCommand;

public interface UserRefreshTokenPort {

	UserRefreshTokenEntity searchByUserId(long userId);

	UserRefreshTokenEntity createAndFlush(CreateRefreshTokenCommand command);
}
