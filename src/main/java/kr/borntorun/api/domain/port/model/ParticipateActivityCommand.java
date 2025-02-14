package kr.borntorun.api.domain.port.model;

public record ParticipateActivityCommand(int activityId,
                                         int myUserId) {}
