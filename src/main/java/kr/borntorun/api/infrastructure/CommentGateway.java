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
    return commentRepository.findAllByFeedId(feedId);
  }

  public List<CommentEntity> searchAll(List<Integer> feedIds) {
    return commentRepository.findAllByFeedIdIn(feedIds);
  }

  public void create(final CreateCommentQuery query) {
    CommentEntity commentEntity = CommentConverter.INSTANCE.toCommentEntity(query);
    commentRepository.save(commentEntity);
  }

  public int qty(final int feedId) {
    return commentRepository.countByFeedId(feedId);
  }

  public CommentEntity search(final int commentId) {
    return commentRepository.findById(commentId)
        .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
  }

  public List<CommentEntity> searchReComments(int commentId) {
    return commentRepository.findAllByParentId(commentId);
  }

  public void remove(final int commentId) {
    commentRepository.deleteById(commentId);
  }

  public void removeAll(final List<Integer> commentIds) {
    commentRepository.deleteAllById(commentIds);
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
