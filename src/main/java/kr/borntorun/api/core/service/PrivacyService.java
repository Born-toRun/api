package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.PrivacyConverter;
import kr.borntorun.api.domain.entity.UserPrivacyEntity;
import kr.borntorun.api.domain.port.PrivacyPort;
import kr.borntorun.api.domain.port.model.ModifyUserPrivacyCommand;
import kr.borntorun.api.domain.port.model.UserPrivacy;
import kr.borntorun.api.infrastructure.PrivacyGateway;
import kr.borntorun.api.infrastructure.model.ModifyUserPrivacyQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrivacyService implements PrivacyPort {

	private final PrivacyConverter privacyConverter;

	private final PrivacyGateway privacyGateway;

	@Transactional
	@Override
	public void modifyUserPrivacy(ModifyUserPrivacyCommand command) {
		ModifyUserPrivacyQuery query = privacyConverter.toModifyUserPrivacyQuery(command);
		privacyGateway.modifyUserPrivacy(query);
	}

	@Transactional(readOnly = true)
	@Override
	public UserPrivacy searchUserPrivacy(long userId) {
		UserPrivacyEntity userPrivacyEntity = privacyGateway.searchUserPrivacy(userId);
		return privacyConverter.toUserPrivacy(userPrivacyEntity);
	}
}
