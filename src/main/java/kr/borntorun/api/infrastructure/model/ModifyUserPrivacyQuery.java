package kr.borntorun.api.infrastructure.model;

public record ModifyUserPrivacyQuery(long myUserId,
									 Boolean isInstagramIdPublic) {
}
