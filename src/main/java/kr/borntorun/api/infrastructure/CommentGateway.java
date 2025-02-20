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

	private final CommentConverter commentConverter;

	private final CommentRepository commentRepository;

	public List<CommentEntity> searchAll(final long feedId) {
		return commentRepository.findAllByFeedId(feedId);
	}

	public List<CommentEntity> searchAll(List<Long> feedIds) {
		return commentRepository.findAllByFeedIdIn(feedIds);
	}

	public void create(final CreateCommentQuery query) {
		CommentEntity commentEntity = commentConverter.toCommentEntity(query);
		commentRepository.save(commentEntity);
	}

	public int qty(final long feedId) {
		return commentRepository.countByFeedId(feedId);
	}

	public CommentEntity search(final long commentId) {
		return commentRepository.findById(commentId)
		  .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
	}

	public List<CommentEntity> searchReComments(long commentId) {
		return commentRepository.findAllByParentId(commentId);
	}

	public void remove(final long commentId) {
		commentRepository.deleteById(commentId);
	}

	public void removeAll(final List<Long> commentIds) {
		commentRepository.deleteAllById(commentIds);
	}

	public void removeAll(final long userId) {
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
