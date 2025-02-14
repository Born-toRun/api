package kr.borntorun.api.infrastructure;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.UserPrivacyRepository;
import kr.borntorun.api.domain.entity.UserPrivacyEntity;
import kr.borntorun.api.infrastructure.model.ModifyUserPrivacyQuery;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PrivacyGateway {

  private final UserPrivacyRepository userPrivacyRepository;

  public void modifyUserPrivacy(final ModifyUserPrivacyQuery query) {
    final UserPrivacyEntity userPrivacy = searchUserPrivacy(query.myUserId());
    userPrivacy.change(query.isGenderPublic(), query.isBirthdayPublic(), query.isInstagramIdPublic());

    userPrivacyRepository.save(userPrivacy);
  }

  public void initUserPrivacy(final int userId) {
    userPrivacyRepository.save(UserPrivacyEntity.builder()
        .userId(userId)
        .build());
  }

  public UserPrivacyEntity searchUserPrivacy(final int userId) {
    return userPrivacyRepository.findByUserId(userId);
  }

  public void remove(final int userId) {
    userPrivacyRepository.deleteById(userPrivacyRepository.findByUserId(userId).getId());
  }
}
