package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.UserRefreshTokenConverter;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.entity.UserRefreshTokenEntity;
import kr.borntorun.api.domain.port.UserRefreshTokenPort;
import kr.borntorun.api.domain.port.model.CreateRefreshTokenCommand;
import kr.borntorun.api.infrastructure.UserGateway;
import kr.borntorun.api.infrastructure.UserRefreshTokenGateway;
import kr.borntorun.api.infrastructure.model.CreateRefreshTokenQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserRefreshTokenService implements UserRefreshTokenPort {

	private final UserRefreshTokenGateway userRefreshTokenGateway;

	private final UserGateway userGateway;

	private final UserRefreshTokenConverter userRefreshTokenConverter;

	@Transactional(readOnly = true)
	@Override
	public UserRefreshTokenEntity searchByUserId(long userId) {
		return userRefreshTokenGateway.searchByUserId(userId);
	}

	@Transactional
	@Override
	public UserRefreshTokenEntity createAndFlush(CreateRefreshTokenCommand command) {
		UserEntity userEntity = userGateway.searchById(command.userId());
		CreateRefreshTokenQuery query = userRefreshTokenConverter.toCreateRefreshTokenQuery(command, userEntity);
		return userRefreshTokenGateway.saveAndFlush(query);
	}
}
