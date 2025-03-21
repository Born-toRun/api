package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;

import kr.borntorun.api.adapter.in.web.payload.CreateYellowCardRequest;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.entity.YellowCardEntity;
import kr.borntorun.api.domain.port.model.CreateYellowCardCommand;
import kr.borntorun.api.infrastructure.model.CreateYellowCardQuery;

@Mapper(componentModel = "spring")
public interface YellowCardConverter {

	CreateYellowCardCommand toCreateYellowCardCommand(CreateYellowCardRequest source, long sourceUserId);

	CreateYellowCardQuery toCreateYellowCardQuery(CreateYellowCardCommand source, UserEntity sourceUser,
	  UserEntity targetUser);

	YellowCardEntity toYellowCardEntity(CreateYellowCardQuery source);
}
