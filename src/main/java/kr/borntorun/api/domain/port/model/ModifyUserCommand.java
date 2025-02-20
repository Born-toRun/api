package kr.borntorun.api.domain.port.model;

public record ModifyUserCommand(long userId,
								long profileImageId,
								String instagramId
) {
}