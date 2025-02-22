package kr.borntorun.api.infrastructure.model;

import kr.borntorun.api.domain.entity.UserEntity;

public record CreateRefreshTokenQuery(long userId, String refreshToken, UserEntity userEntity) {
}
