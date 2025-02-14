package kr.borntorun.api.infrastructure.model;

public record ModifyUserQuery(int userId,
                              int profileImageId,
                              String instagramId) {}
