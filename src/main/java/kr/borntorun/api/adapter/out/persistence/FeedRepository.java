package kr.borntorun.api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.borntorun.api.domain.entity.FeedEntity;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

	@Query(
	  "SELECT distinct f FROM FeedEntity f " +
		"LEFT JOIN FETCH f.userEntity " +
		"LEFT JOIN FETCH f.commentEntities " +
		"LEFT JOIN FETCH f.feedImageMappingEntities " +
		"LEFT JOIN FETCH f.recommendationEntities " +
		"WHERE f.id =:id"
	)
	Optional<FeedEntity> findById(long id);
}
