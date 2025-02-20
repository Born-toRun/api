package kr.borntorun.api.domain.port.model;

public record CreateYellowCardCommand(long targetUserId,
									  long sourceUserId,
									  String reason,
									  String basis) {
}
