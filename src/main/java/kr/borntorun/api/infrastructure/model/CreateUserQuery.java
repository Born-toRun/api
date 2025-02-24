package kr.borntorun.api.infrastructure.model;

import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.domain.constant.RoleType;

public record CreateUserQuery(String socialId, ProviderType providerType, RoleType roleType) {
}
