package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

public record CreateActivityCommand(String title,
                              String contents,
                              LocalDateTime startDate,
                              String venue,
                              String venueUrl,
                              int participantsLimit,
                              int participationFee,
                              String course,
                              String courseDetail,
                              String path,
                              int myUserId) {}