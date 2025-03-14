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
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.domain.port.model.CommentResult;
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
	public List<CommentResult> searchAll(long feedId, TokenDetail my) {
		SearchAllCommentCommand command = new SearchAllCommentCommand(feedId, my.getId());
		return commentService.searchAll(command);
	}

	@Cacheable(key = "'detail: ' + #commentId + #my.id")
	public CommentDetail detail(long commentId, TokenDetail my) {
		DetailCommentCommand command = new DetailCommentCommand(commentId, my.getId());
		return commentService.detail(command);
	}

	@CacheEvict(allEntries = true, cacheNames = {"comment", "feed"})
	public void create(TokenDetail my, long feedId, CreateCommentRequest request) {
		CreateCommentCommand command = commentConverter.toCreateCommentCommand(my.getId(), feedId, request);
		commentService.create(command);
	}

	@Cacheable(key = "'qty: ' + #feedId")
	public int qty(long feedId) {
		return commentService.qty(feedId);
	}

	@CacheEvict(allEntries = true, cacheNames = {"comment", "feed"})
	public void remove(long commentId) {
		commentService.remove(commentId);
	}

	@CacheEvict(allEntries = true)
	public CommentResult modify(long commentId, ModifyCommentRequest request) {
		ModifyCommentCommand command = commentConverter.toModifyCommentCommand(request, commentId);
		return commentService.modify(command);
	}
}
