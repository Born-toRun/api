package kr.borntorun.api.infrastructure.model;

public record ModifyUserPrivacyQuery(int myUserId,
                                     Boolean isGenderPublic,
                                     Boolean isBirthdayPublic,
                                     Boolean isInstagramIdPublic) {}
