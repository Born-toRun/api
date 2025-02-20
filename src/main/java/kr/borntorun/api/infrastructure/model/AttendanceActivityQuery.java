package kr.borntorun.api.infrastructure.model;

public record AttendanceActivityQuery(long activityId,
                                      int accessCode,
									  long myUserId) {}
