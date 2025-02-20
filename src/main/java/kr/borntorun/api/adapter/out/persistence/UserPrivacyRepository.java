package kr.borntorun.api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.borntorun.api.domain.entity.UserPrivacyEntity;

public interface UserPrivacyRepository extends JpaRepository<UserPrivacyEntity, Long> {

	@Query(
	  "SELECT u FROM UserPrivacyEntity u " +
		"JOIN FETCH u.userEntity " +
		"WHERE u.userId =:userId"
	)
	Optional<UserPrivacyEntity> findByUserId(final long userId);
}
