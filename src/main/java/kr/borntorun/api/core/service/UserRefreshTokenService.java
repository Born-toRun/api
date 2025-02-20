package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;

import kr.borntorun.api.core.converter.UserRefreshTokenConverter;
import kr.borntorun.api.domain.entity.UserRefreshTokenEntity;
import kr.borntorun.api.domain.port.UserRefreshTokenPort;
import kr.borntorun.api.domain.port.model.CreateRefreshTokenCommand;
import kr.borntorun.api.infrastructure.UserRefreshTokenGateway;
import kr.borntorun.api.infrastructure.model.CreateRefreshTokenQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserRefreshTokenService implements UserRefreshTokenPort {

	private final UserRefreshTokenGateway userRefreshTokenGateway;

	private final UserRefreshTokenConverter userRefreshTokenConverter;

	@Override
	public UserRefreshTokenEntity searchByUserId(long userId) {
		return userRefreshTokenGateway.searchByUserId(userId);
	}

	@Override
	public UserRefreshTokenEntity saveAndFlush(CreateRefreshTokenCommand command) {
		CreateRefreshTokenQuery query = userRefreshTokenConverter.toCreateRefreshTokenQuery(command);
		return userRefreshTokenGateway.saveAndFlush(query);
	}
}
