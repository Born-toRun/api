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

  private final PrivacyGateway privacyGateway;


  @Transactional
  @Override
  public void modifyUserPrivacy(final ModifyUserPrivacyCommand command) {
    ModifyUserPrivacyQuery query = PrivacyConverter.INSTANCE.toModifyUserPrivacyQuery(command);
    privacyGateway.modifyUserPrivacy(query);
  }

  @Transactional(readOnly = true)
  @Override
  public UserPrivacy searchUserPrivacy(final int userId) {
    UserPrivacyEntity userPrivacyEntity = privacyGateway.searchUserPrivacy(userId);
    return PrivacyConverter.INSTANCE.toUserPrivacy(userPrivacyEntity);
  }
}
