package kr.borntorun.api.domain.port.model;

public record ParticipateCancelActivityCommand(int participationId,
                                               int myUserId) {}
