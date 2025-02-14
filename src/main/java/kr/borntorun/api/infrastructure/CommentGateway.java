package kr.borntorun.api.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.CommentRepository;
import kr.borntorun.api.core.converter.CommentConverter;
import kr.borntorun.api.domain.entity.CommentEntity;
import kr.borntorun.api.infrastructure.model.CreateCommentQuery;
import kr.borntorun.api.infrastructure.model.ModifyCommentQuery;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentGateway {

  private final CommentRepository commentRepository;

  public List<CommentEntity> searchAll(final int feedId) {
    return commentRepository.findAllByFeedIdAndIsDeletedFalse(feedId);
  }

  public List<CommentEntity> searchAll(List<Integer> feedIds) {
    return commentRepository.findAllByFeedIdInAndIsDeletedFalse(feedIds);
  }

  public void create(final CreateCommentQuery query) {
    commentRepository.save(CommentConverter.INSTANCE.toCommentEntity(query));
  }

  public int qty(final int feedId) {
    return commentRepository.countByFeedIdAndIsDeletedFalse(feedId);
  }

  public CommentEntity search(final int commentId) {
    return commentRepository.findByIdAndIsDeletedFalse(commentId)
        .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
  }

  public List<CommentEntity> searchReComments(int commentId) {
    return commentRepository.findAllByParentIdAndIsDeletedFalse(commentId);
  }

  public void remove(final int commentId) {
    final CommentEntity comment = search(commentId);

    comment.remove();
    commentRepository.save(comment);
  }

  public void removeAll(final List<Integer> commentIds) {
    final List<CommentEntity> comments = commentRepository.findAllById(commentIds);

    comments.forEach(CommentEntity::remove);
    commentRepository.saveAll(comments);
  }

  public void removeAll(final int userId) {
    commentRepository.deleteAllById(commentRepository.findAllByUserId(userId).stream()
        .map(CommentEntity::getId)
        .collect(Collectors.toList()));
  }

  public CommentEntity modify(final ModifyCommentQuery query) {
    final CommentEntity comment = search(query.commentId());
    comment.setContents(query.contents());

    return commentRepository.save(comment);
  }
}
