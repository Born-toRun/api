package kr.borntorun.api.core.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.CommentConverter;
import kr.borntorun.api.core.converter.UserConverter;
import kr.borntorun.api.domain.entity.CommentEntity;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.CommentPort;
import kr.borntorun.api.domain.port.model.BornToRunUser;
import kr.borntorun.api.domain.port.model.Comment;
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.domain.port.model.CreateCommentCommand;
import kr.borntorun.api.domain.port.model.DetailCommentCommand;
import kr.borntorun.api.domain.port.model.ModifyCommentCommand;
import kr.borntorun.api.domain.port.model.SearchAllCommentCommand;
import kr.borntorun.api.infrastructure.CommentGateway;
import kr.borntorun.api.infrastructure.model.CreateCommentQuery;
import kr.borntorun.api.infrastructure.model.ModifyCommentQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService implements CommentPort {

	private final UserConverter userConverter;

	private final CommentConverter commentConverter;

	private final CommentGateway commentGateway;

	@Transactional(readOnly = true)
	@Override
	public List<Comment> searchAll(final SearchAllCommentCommand command) {
		final List<CommentEntity> commentEntities = commentGateway.searchAll(command.feedId());
		final Map<Long, BornToRunUser> writersByUserIdMapping = getWritersByUserId(commentEntities);

		// 댓글/대댓글 정렬
		commentEntities.sort(Comparator
		  .comparingLong((CommentEntity c) -> c.isRootComment() ? c.getId() : c.getParentId())
		  .thenComparingLong(c -> c.isRootComment() ? 0 : -1)
		  .thenComparingLong(CommentEntity::getId)
		  .reversed()
		);

		final List<Comment> comments = commentConverter.toComments(commentEntities, writersByUserIdMapping,
		  command.myUserId());

		final Map<Long, List<Comment>> commentsByParentId = comments.stream()
		  .collect(Collectors.groupingBy(Comment::getParentId));

		for (Comment comment : comments) {
			comment.setReCommentQty(commentsByParentId.getOrDefault(comment.getId(), Collections.emptyList()).size());
		}

		return comments;
	}

	@Transactional(readOnly = true)
	@Override
	public CommentDetail detail(final DetailCommentCommand command) {
		final CommentEntity parentComment = commentGateway.search(command.commentId());

		final List<CommentEntity> reCommentEntities = commentGateway.searchReComments(command.commentId());
		final Map<Long, BornToRunUser> reCommentWritersByUserId = getWritersByUserId(reCommentEntities);

		List<Comment> reComments = reCommentEntities.stream()
		  .map(commentEntity -> commentConverter.toComment(commentEntity,
			reCommentWritersByUserId.get(commentEntity.getUserId())))
		  .sorted(Comparator.comparingLong(Comment::getId)
			.reversed()).toList();

		return commentConverter.toCommentDetail(parentComment, reComments);
	}

	@Transactional
	@Override
	public void create(final CreateCommentCommand command) {
		CreateCommentQuery query = commentConverter.toCreateCommentQuery(command);
		commentGateway.create(query);
	}

	@Transactional(readOnly = true)
	@Override
	public int qty(final long feedId) {
		return commentGateway.qty(feedId);
	}

	@Transactional
	@Override
	public void remove(final long commentId) {
		commentGateway.remove(commentId);
	}

	@Transactional
	@Override
	public Comment modify(final ModifyCommentCommand command) {
		ModifyCommentQuery query = commentConverter.toModifyCommentQuery(command);
		final CommentEntity modified = commentGateway.modify(query);
		return commentConverter.toComment(modified);
	}

	@Transactional(readOnly = true)
	@Override
	public CommentEntity search(final long commentId) {
		return commentGateway.search(commentId);
	}

	private Map<Long, BornToRunUser> getWritersByUserId(final List<CommentEntity> comments) {
		final List<UserEntity> writers = comments.parallelStream()
		  .map(CommentEntity::getUserEntity)
		  .toList();

		return writers.stream()
		  .collect(Collectors.toMap(UserEntity::getId, userConverter::toBornToRunUser));
	}
}
