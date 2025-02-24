package kr.borntorun.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.borntorun.api.domain.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query(
	  "SELECT u FROM UserEntity u " +
		"LEFT JOIN FETCH u.crewEntity " +
		"LEFT JOIN FETCH u.objectStorageEntity " +
		"WHERE u.id =:id"
	)
	Optional<UserEntity> findById(long id);

	@Query(
	  "SELECT distinct u FROM UserEntity u " +
		"LEFT JOIN FETCH u.crewEntity " +
		"LEFT JOIN FETCH u.objectStorageEntity " +
		"LEFT JOIN FETCH u.userRefreshTokenEntity " +
		"LEFT JOIN FETCH u.userPrivacyEntity " +
		"LEFT JOIN FETCH u.feedEntities " +
		"LEFT JOIN FETCH u.activityEntities " +
		"LEFT JOIN FETCH u.activityParticipationEntities " +
		"LEFT JOIN FETCH u.commentEntities " +
		"LEFT JOIN FETCH u.marathonBookmarkEntities " +
		"LEFT JOIN FETCH u.recommendationEntities " +
		"WHERE u.id =:id"
	)
	Optional<UserEntity> findAllEntitiesById(long id);

	List<UserEntity> findAllByNameContaining(String userName);

	@Query(
	  "SELECT u FROM UserEntity u " +
		"LEFT JOIN FETCH u.objectStorageEntity " +
		"LEFT JOIN FETCH u.userPrivacyEntity " +
		"WHERE u.socialId =:socialId"
	)
	Optional<UserEntity> findBySocialId(String socialId);

	boolean existsBySocialId(String socialId);
}
