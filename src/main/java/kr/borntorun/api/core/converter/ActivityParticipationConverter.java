package kr.borntorun.api.core.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.borntorun.api.domain.entity.ActivityParticipationEntity;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.model.AttendanceResult;
import kr.borntorun.api.domain.port.model.ParticipateActivityCommand;
import kr.borntorun.api.infrastructure.model.ParticipateActivityQuery;

@Mapper(componentModel = "spring")
public interface ActivityParticipationConverter {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "registeredAt", ignore = true)
  @Mapping(target = "isAttendance", ignore = true)
  @Mapping(target = "activityEntity", ignore = true)
  @Mapping(target = "userEntity", ignore = true)
  @Mapping(target = "userId", source = "myUserId")
  ActivityParticipationEntity toActivityParticipationEntity(final ParticipateActivityQuery source);

  ParticipateActivityQuery toParticipateActivityQuery(final ParticipateActivityCommand source);

  List<AttendanceResult.Participant> toParticipants(final List<UserEntity> source);

  @Mapping(target = "userId", source = "id")
  @Mapping(target = "userName", source = "name")
  @Mapping(target = "crewName", source = "crewEntity.name")
  @Mapping(target = "userProfileUri", source = "profileImageUri")
  AttendanceResult.Participant toParticipant(UserEntity source);

  @Mapping(target = "host", expression = "java(toParticipant(host))")
  @Mapping(target = "participants", expression = "java(toParticipants(participants))")
  AttendanceResult toAttendanceResult(UserEntity host, List<UserEntity> participants);
}
