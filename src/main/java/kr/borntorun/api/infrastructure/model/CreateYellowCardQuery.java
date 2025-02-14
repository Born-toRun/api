package kr.borntorun.api.infrastructure.model;

public record CreateYellowCardQuery(int targetUserId,
                                    int sourceUserId,
                                    String reason,
                                    String basis) {}
