package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.adapter.in.web.payload.ModifyUserRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyUserResponse;
import kr.borntorun.api.adapter.in.web.payload.SignInRequest;
import kr.borntorun.api.adapter.in.web.payload.SignUpRequest;
import kr.borntorun.api.adapter.in.web.payload.UserDetailResponse;
import kr.borntorun.api.adapter.out.thirdparty.model.AuthSignInRequest;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.ModifyUserCommand;
import kr.borntorun.api.domain.port.model.SignInCommand;
import kr.borntorun.api.domain.port.model.SignUpCommand;
import kr.borntorun.api.infrastructure.model.ModifyUserQuery;
import kr.borntorun.api.infrastructure.model.SignUpUserQuery;

@Mapper
public interface UserConverter {

  UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

  SignInCommand toSignInCommand(final SignInRequest source);

  @Mapping(target = "code", source = "kakaoAuthCode")
  AuthSignInRequest toAuthSignUpRequest(final SignInCommand source);

  SignUpCommand toSignUpCommand(final SignUpRequest source, final int userId);

  @Mapping(target = "userId", source = "id")
  @Mapping(target = "userName", source = "name")
  @Mapping(target = "crewName", source = "crewEntity.name")
  @Mapping(target = "isInstagramIdPublic", source = "userPrivacyEntity.isInstagramIdPublic")
  BornToRunUser toBornToRunUser(final UserEntity source);

  UserDetailResponse toUserDetailResponse(final BornToRunUser source);

  ModifyUserCommand toModifyUserCommand(final ModifyUserRequest source, final int userId);

  ModifyUserQuery toModifyUserQuery(final ModifyUserCommand source);

  SignUpUserQuery toSignUpUserQuery(final SignUpCommand source);

  ModifyUserResponse toModifyUserResponse(final BornToRunUser source);
}
