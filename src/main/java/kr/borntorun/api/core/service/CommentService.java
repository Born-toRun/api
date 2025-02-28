package kr.borntorun.api.core.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.CommentConverter;
import kr.borntorun.api.domain.entity.CommentEntity;
import kr.borntorun.api.domain.port.CommentPort;
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.domain.port.model.CommentResult;
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

	private final CommentConverter commentConverter;

	private final CommentGateway commentGateway;

	@Transactional(readOnly = true)
	@Override
	public List<CommentResult> searchAll(final SearchAllCommentCommand command) {
		final List<CommentEntity> commentEntities = commentGateway.searchAll(command.feedId());

		// 댓글/대댓글 정렬
		commentEntities.sort(Comparator
		  .comparingLong((CommentEntity c) -> c.isRootComment() ? c.getId() : c.getParentId())
		  .thenComparingLong(c -> c.isRootComment() ? 0 : -1)
		  .thenComparingLong(CommentEntity::getId)
		  .reversed()
		);

		return commentConverter.toCommentResult(commentEntities, command.myUserId());
	}

	@Transactional(readOnly = true)
	@Override
	public CommentDetail detail(final DetailCommentCommand command) {
		final CommentEntity parentComment = commentGateway.search(command.commentId());

		final List<CommentEntity> reCommentEntities = commentGateway.searchReComments(command.commentId());

		List<CommentResult> reCommentResults = reCommentEntities.stream()
		  .map(re -> commentConverter.toCommentResult(re, command.myUserId()))
		  .sorted(Comparator.comparingLong(CommentResult::id)
			.reversed()).toList();

		return commentConverter.toCommentDetail(parentComment, reCommentResults);
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
	public CommentResult modify(final ModifyCommentCommand command) {
		ModifyCommentQuery query = commentConverter.toModifyCommentQuery(command);
		final CommentEntity modified = commentGateway.modify(query);
		return commentConverter.toCommentResult(modified);
	}

	@Transactional(readOnly = true)
	@Override
	public CommentEntity search(final long commentId) {
		return commentGateway.search(commentId);
	}

	// private Map<Long, BornToRunUser> getWritersByUserId(final List<CommentEntity> comments) {
	// 	final List<UserEntity> writers = comments.parallelStream()
	// 	  .map(CommentEntity::getUserEntity)
	// 	  .toList();
	//
	// 	return writers.stream()
	// 	  .collect(Collectors.toMap(UserEntity::getId, userConverter::toBornToRunUser));
	// }
}
