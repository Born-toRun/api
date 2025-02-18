package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.SettingUserPrivacyRequest;
import kr.borntorun.api.core.converter.PrivacyConverter;
import kr.borntorun.api.core.service.PrivacyService;
import kr.borntorun.api.domain.port.model.ModifyUserPrivacyCommand;
import kr.borntorun.api.domain.port.model.UserPrivacy;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "privacy")
public class PrivacyProxy {

  private final PrivacyConverter privacyConverter;

  private final PrivacyService privacyService;

  @CacheEvict(allEntries = true)
  public void modifyUserPrivacy(final SettingUserPrivacyRequest request, final int myUserId) {
    ModifyUserPrivacyCommand command = privacyConverter.toModifyUserPrivacyCommand(request, myUserId);
    privacyService.modifyUserPrivacy(command);
  }

  @Cacheable(key = "'searchUserPrivacy: ' + #userId")
  public UserPrivacy searchUserPrivacy(final int userId) {
    return privacyService.searchUserPrivacy(userId);
  }
}
