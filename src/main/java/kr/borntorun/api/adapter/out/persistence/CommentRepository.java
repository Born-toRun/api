package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.borntorun.api.domain.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	List<CommentEntity> findAllByParentId(long parentId);

	@Query(
	  "SELECT distinct c FROM CommentEntity c " +
		"LEFT JOIN FETCH c.feedEntity " +
		"LEFT JOIN FETCH c.userEntity " +
		"LEFT JOIN FETCH c.recommendationEntities " +
		"WHERE c.feedId =:feedId"
	)
	List<CommentEntity> findAllByFeedId(long feedId);

	List<CommentEntity> findAllByFeedIdIn(List<Long> feedIds);

	int countByFeedId(long feedId);

	List<CommentEntity> findAllByUserId(long userId);
}
