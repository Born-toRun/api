package kr.borntorun.api.adapter.in.web.proxy;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.CreateCommentRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyCommentRequest;
import kr.borntorun.api.core.converter.CommentConverter;
import kr.borntorun.api.core.service.CommentService;
import kr.borntorun.api.domain.port.model.Comment;
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "comment")
public class CommentProxy {

  private final CommentService commentService;

  @Cacheable(key = "'searchAll: ' + #feedId + #my.id")
  public List<Comment> searchAll(final int feedId, final TokenDetail my) {
    return commentService.searchAll(CommentConverter.INSTANCE.toSearchAllCommentCommand(feedId, my.getId()));
  }

  @Cacheable(key = "'detail: ' + #commentId + #my.id")
  public CommentDetail detail(final int commentId, final TokenDetail my) {
    return commentService.detail(CommentConverter.INSTANCE.toDetailCommentCommand(commentId, my.getId()));
  }

  @CacheEvict(allEntries = true, cacheNames = {"comment", "feed"})
  public void create(final TokenDetail my, final int feedId, final CreateCommentRequest request) {
    commentService.create(CommentConverter.INSTANCE.toCreateCommentCommand(my.getId(), feedId, request));
  }

  @Cacheable(key = "'qty: ' + #feedId")
  public int qty(final int feedId) {
    return commentService.qty(feedId);
  }

  @CacheEvict(allEntries = true, cacheNames = {"comment", "feed"})
  public void remove(final int commentId) {
    commentService.remove(commentId);
  }

  @CacheEvict(allEntries = true)
  public Comment modify(final int commentId, final ModifyCommentRequest request) {
    return commentService.modify(CommentConverter.INSTANCE.toModifyCommentCommand(request, commentId));
  }
}
