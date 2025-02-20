package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

public record ModifyActivityCommand(long activityId,
									String title,
									String contents,
									LocalDateTime startDate,
									String venue,
									String venueUrl,
									int participantsLimit,
									int participationFee,
									String course,
									String courseDetail,
									String path) {
}
