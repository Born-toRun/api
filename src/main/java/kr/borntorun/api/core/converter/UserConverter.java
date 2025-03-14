package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.borntorun.api.adapter.in.web.payload.ModifyUserRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyUserResponse;
import kr.borntorun.api.adapter.in.web.payload.SignUpRequest;
import kr.borntorun.api.adapter.in.web.payload.UserDetailResponse;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.CreateUserCommand;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;
import kr.borntorun.api.infrastructure.model.CreateUserQuery;
import kr.borntorun.api.infrastructure.model.ModifyUserQuery;
import kr.borntorun.api.infrastructure.model.SignUpUserQuery;

@Mapper(componentModel = "spring")
public interface UserConverter {

	SignUpCommand toSignUpCommand(SignUpRequest source, long userId);

	@Mapping(target = "userId", source = "id")
	@Mapping(target = "userName", source = "name")
	@Mapping(target = "crewName", source = "crewEntity.name")
	@Mapping(target = "isInstagramIdPublic", source = "userPrivacyEntity.isInstagramIdPublic")
	@Mapping(target = "refreshToken", source = "userRefreshTokenEntity.refreshToken")
	BornToRunUser toBornToRunUser(UserEntity source);

	UserDetailResponse toUserDetailResponse(BornToRunUser source);

	ModifyUserCommand toModifyUserCommand(ModifyUserRequest source, long userId);

	ModifyUserQuery toModifyUserQuery(ModifyUserCommand source);

	SignUpUserQuery toSignUpUserQuery(SignUpCommand source);

	ModifyUserResponse toModifyUserResponse(BornToRunUser source);

	CreateUserQuery toCreateUserQuery(CreateUserCommand source);
}
