package kr.borntorun.api.infrastructure.model;

public record AttendanceActivityQuery(int activityId,
                                      int accessCode,
                                      int myUserId) {}
