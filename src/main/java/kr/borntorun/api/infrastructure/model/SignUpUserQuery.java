package kr.borntorun.api.infrastructure.model;

public record SignUpUserQuery(int userId,
                              String userName,
                              int crewId,
                              String instagramId) {}
