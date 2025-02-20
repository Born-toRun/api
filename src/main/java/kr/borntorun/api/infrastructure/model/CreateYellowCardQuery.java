package kr.borntorun.api.infrastructure.model;

public record CreateYellowCardQuery(long targetUserId,
									long sourceUserId,
                                    String reason,
                                    String basis) {}
