package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.borntorun.api.adapter.in.web.payload.SearchUserPrivacyResponse;
import kr.borntorun.api.adapter.in.web.payload.SettingUserPrivacyRequest;
import kr.borntorun.api.domain.entity.UserPrivacyEntity;
import kr.borntorun.api.domain.port.model.ModifyUserPrivacyCommand;
import kr.borntorun.api.domain.port.model.UserPrivacy;
import kr.borntorun.api.infrastructure.model.ModifyUserPrivacyQuery;

@Mapper(componentModel = "spring")
public interface PrivacyConverter {

	ModifyUserPrivacyCommand toModifyUserPrivacyCommand(SettingUserPrivacyRequest source, long myUserId);

	SearchUserPrivacyResponse toSearchUserPrivacyResponse(UserPrivacy source);

	@Mapping(target = "id", source = "id")
	UserPrivacy toUserPrivacy(UserPrivacyEntity source);

	ModifyUserPrivacyQuery toModifyUserPrivacyQuery(ModifyUserPrivacyCommand source);
}
