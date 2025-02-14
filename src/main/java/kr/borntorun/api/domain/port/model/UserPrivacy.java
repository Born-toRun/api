package kr.borntorun.api.domain.port.model;

public record UserPrivacy(int privacyId,
                          int userId,
                          Boolean isGenderPublic,
                          Boolean isBirthdayPublic,
                          Boolean isInstagramIdPublic) {}
