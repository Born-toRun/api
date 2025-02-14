package kr.borntorun.api.domain.port.model;

public record CreateYellowCardCommand(int targetUserId,
                                      int sourceUserId,
                                      String reason,
                                      String basis) {}
