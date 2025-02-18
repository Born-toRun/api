package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.borntorun.api.domain.entity.ActivityParticipationEntity;
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
}
