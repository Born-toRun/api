package kr.borntorun.api.domain.port.model;

public record AttendanceActivityCommand(long activityId,
										int accessCode,
										long myUserId) {
}
