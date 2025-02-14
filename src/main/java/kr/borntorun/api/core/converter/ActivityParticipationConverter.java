package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.domain.entity.ActivityParticipationEntity;
import kr.borntorun.api.domain.port.model.ParticipateActivityCommand;
import kr.borntorun.api.infrastructure.model.ParticipateActivityQuery;

@Mapper
public interface ActivityParticipationConverter {

  ActivityParticipationConverter INSTANCE = Mappers.getMapper(ActivityParticipationConverter.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "registeredAt", ignore = true)
  @Mapping(target = "isDeleted", ignore = true)
  @Mapping(target = "isAttendance", ignore = true)
  @Mapping(target = "activityEntity", ignore = true)
  @Mapping(target = "userEntity", ignore = true)
  @Mapping(target = "userId", source = "myUserId")
  ActivityParticipationEntity toActivityParticipationEntity(final ParticipateActivityQuery source);

  ParticipateActivityQuery toParticipateActivityQuery(final ParticipateActivityCommand source);
}
