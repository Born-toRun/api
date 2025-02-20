package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

	List<CommentEntity> findAllByParentId(final long parentId);

	List<CommentEntity> findAllByFeedId(final long feedId);

	List<CommentEntity> findAllByFeedIdIn(final List<Long> feedIds);

	int countByFeedId(final long feedId);

	List<CommentEntity> findAllByUserId(final long userId);
}
