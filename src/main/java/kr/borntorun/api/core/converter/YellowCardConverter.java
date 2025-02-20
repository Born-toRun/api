package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.borntorun.api.adapter.in.web.payload.CreateYellowCardRequest;
import kr.borntorun.api.domain.entity.YellowCardEntity;
import kr.borntorun.api.domain.port.model.CreateYellowCardCommand;
import kr.borntorun.api.infrastructure.model.CreateYellowCardQuery;

@Mapper(componentModel = "spring")
public interface YellowCardConverter {

	CreateYellowCardCommand toCreateYellowCardCommand(final CreateYellowCardRequest source, final long sourceUserId);

	CreateYellowCardQuery toCreateYellowCardQuery(final CreateYellowCardCommand source);

	@Mapping(target = "userEntity", ignore = true)
	YellowCardEntity toYellowCardEntity(final CreateYellowCardQuery source);
}
