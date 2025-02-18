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
import kr.borntorun.api.domain.port.model.CreateCommentCommand;
import kr.borntorun.api.domain.port.model.DetailCommentCommand;
import kr.borntorun.api.domain.port.model.ModifyCommentCommand;
import kr.borntorun.api.domain.port.model.SearchAllCommentCommand;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "comment")
public class CommentProxy {

  private final CommentConverter commentConverter;

  private final CommentService commentService;

  @Cacheable(key = "'searchAll: ' + #feedId + #my.id")
  public List<Comment> searchAll(final int feedId, final TokenDetail my) {
    SearchAllCommentCommand command = commentConverter.toSearchAllCommentCommand(feedId, my.getId());
    return commentService.searchAll(command);
  }

  @Cacheable(key = "'detail: ' + #commentId + #my.id")
  public CommentDetail detail(final int commentId, final TokenDetail my) {
    DetailCommentCommand command = commentConverter.toDetailCommentCommand(commentId, my.getId());
    return commentService.detail(command);
  }

  @CacheEvict(allEntries = true, cacheNames = {"comment", "feed"})
  public void create(final TokenDetail my, final int feedId, final CreateCommentRequest request) {
    CreateCommentCommand command = commentConverter.toCreateCommentCommand(my.getId(), feedId, request);
    commentService.create(command);
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
    ModifyCommentCommand command = commentConverter.toModifyCommentCommand(request, commentId);
    return commentService.modify(command);
  }
}
