package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.CommentEntity;


public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

  List<CommentEntity> findAllByParentId(final int parentId);
  List<CommentEntity> findAllByFeedId(final int feedId);
  List<CommentEntity> findAllByFeedIdIn(final List<Integer> feedIds);
  int countByFeedId(final int feedId);
  List<CommentEntity> findAllByUserId(final int userId);
}
