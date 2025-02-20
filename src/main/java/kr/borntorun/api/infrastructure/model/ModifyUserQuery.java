package kr.borntorun.api.infrastructure.model;

public record ModifyUserQuery(long userId,
							  long profileImageId,
                              String instagramId) {}
