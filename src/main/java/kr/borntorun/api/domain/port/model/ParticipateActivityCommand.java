package kr.borntorun.api.domain.port.model;

public record ParticipateActivityCommand(long activityId,
										 long myUserId) {
}
