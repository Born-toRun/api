package kr.borntorun.api.infrastructure.model;

import kr.borntorun.api.domain.constant.ProviderType;

public record CreateGuestQuery(String socialId, ProviderType providerType) {
}
