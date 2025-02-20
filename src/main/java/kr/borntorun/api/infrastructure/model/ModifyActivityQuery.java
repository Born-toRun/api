package kr.borntorun.api.infrastructure.model;

import java.time.LocalDateTime;

public record ModifyActivityQuery(long activityId,
                                  String title,
                                  String contents,
                                  LocalDateTime startDate,
                                  String venue,
                                  String venueUrl,
                                  int participantsLimit,
                                  int participationFee,
                                  String course,
                                  String courseDetail,
                                  String path) {}
