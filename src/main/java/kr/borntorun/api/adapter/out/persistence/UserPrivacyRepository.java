package kr.borntorun.api.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.borntorun.api.domain.entity.UserPrivacyEntity;


public interface UserPrivacyRepository extends JpaRepository<UserPrivacyEntity, Integer> {

  @Query(
      "SELECT u FROM UserPrivacyEntity u " +
          "JOIN FETCH u.userEntity " +
          "WHERE u.userId =:userId"
  )
  UserPrivacyEntity findByUserId(final int userId);
}
