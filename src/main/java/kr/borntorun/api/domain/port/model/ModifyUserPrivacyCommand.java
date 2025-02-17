package kr.borntorun.api.domain.port.model;

public record ModifyUserPrivacyCommand(int myUserId,
                                       Boolean isInstagramIdPublic) {}
