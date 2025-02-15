package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

import kr.borntorun.api.domain.constant.ActivityRecruitmentType;

public record Activity(int id,
                       String title,
                       String contents,
                       LocalDateTime startDate,
                       String venue,
                       String venueUrl,
                       int participantsLimit,
                       int participantsQty,
                       int participationFee,
                       String course,
                       String courseDetail,
                       String path,
                       int attendanceCode,
                       boolean isOpen,
                       LocalDateTime updatedAt,
                       LocalDateTime registeredAt,
                       ActivityRecruitmentType recruitmentType,
                       Host host) {

  public record Host(int userId,
                     int crewId,
                     String userProfileUri,
                     String userName,
                     String crewName,
                     Boolean isManager,
                     Boolean isAdmin) {}
}
