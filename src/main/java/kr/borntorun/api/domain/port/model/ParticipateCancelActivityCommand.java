package kr.borntorun.api.domain.port.model;

public record ParticipateCancelActivityCommand(long participationId,
											   long myUserId) {}
