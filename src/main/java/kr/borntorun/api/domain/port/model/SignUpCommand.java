package kr.borntorun.api.domain.port.model;

public record SignUpCommand(
    int userId,
    String userName,
    int crewId,
    String instagramId
) {
}