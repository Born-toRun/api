package kr.borntorun.api.domain.port.model;

public record ModifyUserPrivacyCommand(long myUserId,
									   Boolean isInstagramIdPublic) {
}
