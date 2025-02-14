package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.adapter.in.web.payload.CreateYellowCardRequest;
import kr.borntorun.api.domain.entity.YellowCardEntity;
import kr.borntorun.api.domain.port.model.CreateYellowCardCommand;
import kr.borntorun.api.infrastructure.model.CreateYellowCardQuery;

@Mapper
public interface YellowCardConverter {

  YellowCardConverter INSTANCE = Mappers.getMapper(YellowCardConverter.class);

  CreateYellowCardCommand toCreateYellowCardCommand(final CreateYellowCardRequest source, final int sourceUserId);

  CreateYellowCardQuery toCreateYellowCardQuery(final CreateYellowCardCommand source);

  @Mapping(target = "userEntity", ignore = true)
  YellowCardEntity toYellowCardEntity(final CreateYellowCardQuery source);
}
