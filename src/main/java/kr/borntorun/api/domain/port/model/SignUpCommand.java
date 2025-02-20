package kr.borntorun.api.domain.port.model;

public record SignUpCommand(
  long userId,
    String userName,
    Long crewId,
    String instagramId
) {
}