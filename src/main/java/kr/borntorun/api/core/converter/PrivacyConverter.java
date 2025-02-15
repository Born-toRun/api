package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.adapter.in.web.payload.SearchUserPrivacyResponse;
import kr.borntorun.api.adapter.in.web.payload.SettingUserPrivacyRequest;
import kr.borntorun.api.domain.entity.UserPrivacyEntity;
import kr.borntorun.api.domain.port.model.ModifyUserPrivacyCommand;
import kr.borntorun.api.domain.port.model.UserPrivacy;
import kr.borntorun.api.infrastructure.model.ModifyUserPrivacyQuery;

@Mapper
public interface PrivacyConverter {

  PrivacyConverter INSTANCE = Mappers.getMapper(PrivacyConverter.class);

  ModifyUserPrivacyCommand toSettingUserPrivacyCommand(final SettingUserPrivacyRequest source, final int myUserId);

  SearchUserPrivacyResponse toSearchUserPrivacyResponse(final UserPrivacy source);

  @Mapping(target = "id", source = "id")
  UserPrivacy toUserPrivacy(final UserPrivacyEntity source);

  ModifyUserPrivacyQuery toModifyUserPrivacyQuery(final ModifyUserPrivacyCommand source);
}
