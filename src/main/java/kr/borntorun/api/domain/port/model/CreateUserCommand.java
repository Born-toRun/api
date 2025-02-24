package kr.borntorun.api.domain.port.model;

import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.domain.constant.RoleType;

public record CreateUserCommand(String socialId, ProviderType providerType, RoleType roleType) {
}
