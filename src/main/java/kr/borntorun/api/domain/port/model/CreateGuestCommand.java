package kr.borntorun.api.domain.port.model;

import kr.borntorun.api.domain.constant.ProviderType;

public record CreateGuestCommand(String socialId, ProviderType providerType) {
}
