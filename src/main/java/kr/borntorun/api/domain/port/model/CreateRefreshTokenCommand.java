package kr.borntorun.api.domain.port.model;

public record CreateRefreshTokenCommand(long userId, String refreshToken) {
}
