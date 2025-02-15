package kr.borntorun.api.infrastructure;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.UserPrivacyRepository;
import kr.borntorun.api.domain.entity.UserPrivacyEntity;
import kr.borntorun.api.infrastructure.model.ModifyUserPrivacyQuery;
import kr.borntorun.api.support.exception.NotFoundException;
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

  public UserPrivacyEntity searchUserPrivacy(final int userId) {
    return userPrivacyRepository.findByUserId(userId)
      .orElseThrow(() -> new NotFoundException("정보 노출 동의 내용을 찾을 수 없습니다."));
  }

  public void remove(final int userId) {
    UserPrivacyEntity userPrivacyEntity = searchUserPrivacy(userId);
    userPrivacyRepository.deleteById(userPrivacyEntity.getId());
  }
}
