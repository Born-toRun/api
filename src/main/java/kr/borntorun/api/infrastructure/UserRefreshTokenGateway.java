package kr.borntorun.api.infrastructure;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.UserRefreshTokenRepository;
import kr.borntorun.api.domain.entity.UserRefreshTokenEntity;
import kr.borntorun.api.infrastructure.model.CreateRefreshTokenQuery;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRefreshTokenGateway {

	private final UserRefreshTokenRepository userRefreshTokenRepository;

	public UserRefreshTokenEntity searchByUserId(long userId) {
		return userRefreshTokenRepository.findByUserId(userId)
		  .orElse(null);
	}

	public UserRefreshTokenEntity saveAndFlush(CreateRefreshTokenQuery query) {
		UserRefreshTokenEntity userRefreshTokenEntity = UserRefreshTokenEntity.builder()
		  .userId(query.userId())
		  .refreshToken(query.refreshToken())
		  .build();

		userRefreshTokenEntity.add(query.userEntity());

		return userRefreshTokenRepository.saveAndFlush(userRefreshTokenEntity);
	}
}
