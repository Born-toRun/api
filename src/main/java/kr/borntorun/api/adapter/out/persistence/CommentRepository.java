package kr.borntorun.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.entity.CommentEntity;


public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

  Optional<CommentEntity> findByIdAndIsDeletedFalse(final int id);
  List<CommentEntity> findAllByParentIdAndIsDeletedFalse(final int parentId);
  List<CommentEntity> findAllByFeedIdAndIsDeletedFalse(final int feedId);
  List<CommentEntity> findAllByFeedIdInAndIsDeletedFalse(final List<Integer> feedIds);
  int countByFeedIdAndIsDeletedFalse(final int feedId);
  List<CommentEntity> findAllByUserId(final int userId);
}
