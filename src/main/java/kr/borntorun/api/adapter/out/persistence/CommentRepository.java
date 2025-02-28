package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.borntorun.api.domain.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	List<CommentEntity> findAllByParentId(final long parentId);

	@Query(
	  "SELECT distinct c FROM CommentEntity c " +
		"LEFT JOIN FETCH c.feedEntity " +
		"LEFT JOIN FETCH c.userEntity " +
		"LEFT JOIN FETCH c.recommendationEntities " +
		"WHERE c.feedId =:feedId"
	)
	List<CommentEntity> findAllByFeedId(final long feedId);

	List<CommentEntity> findAllByFeedIdIn(final List<Long> feedIds);

	int countByFeedId(final long feedId);

	List<CommentEntity> findAllByUserId(final long userId);
}
