package kr.borntorun.api.infrastructure.model;

public record SignUpUserQuery(long userId,
							  String userName,
							  Long crewId,
							  String instagramId) {
}
