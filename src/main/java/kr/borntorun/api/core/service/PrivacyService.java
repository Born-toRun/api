package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.PrivacyConverter;
import kr.borntorun.api.domain.port.PrivacyPort;
import kr.borntorun.api.domain.port.model.ModifyUserPrivacyCommand;
import kr.borntorun.api.domain.port.model.UserPrivacy;
import kr.borntorun.api.infrastructure.PrivacyGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrivacyService implements PrivacyPort {

  private final PrivacyGateway privacyGateway;


  @Transactional
  @Override
  public void modifyUserPrivacy(final ModifyUserPrivacyCommand command) {
    privacyGateway.modifyUserPrivacy(PrivacyConverter.INSTANCE.toModifyUserPrivacyQuery(command));
  }

  @Transactional(readOnly = true)
  @Override
  public UserPrivacy searchUserPrivacy(final int userId) {
    return PrivacyConverter.INSTANCE.toUserPrivacy(privacyGateway.searchUserPrivacy(userId));
  }
}
