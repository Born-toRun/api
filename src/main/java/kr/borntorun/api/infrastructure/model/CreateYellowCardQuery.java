package kr.borntorun.api.infrastructure.model;

import kr.borntorun.api.domain.entity.UserEntity;

public record CreateYellowCardQuery(long targetUserId,
									long sourceUserId,
									String reason,
									String basis,
									UserEntity sourceUser,
									UserEntity targetUser) {
}
