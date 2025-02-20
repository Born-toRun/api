package kr.borntorun.api.infrastructure.model;

public record CreateRefreshTokenQuery(long userId, String refreshToken) {
}
