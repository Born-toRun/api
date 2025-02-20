package kr.borntorun.api.domain.port;

import java.util.List;

import kr.borntorun.api.domain.entity.CommentEntity;
import kr.borntorun.api.domain.port.model.Comment;
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.domain.port.model.CreateCommentCommand;
import kr.borntorun.api.domain.port.model.DetailCommentCommand;
import kr.borntorun.api.domain.port.model.ModifyCommentCommand;
import kr.borntorun.api.domain.port.model.SearchAllCommentCommand;

public interface CommentPort {

	List<Comment> searchAll(final SearchAllCommentCommand command);

	CommentDetail detail(final DetailCommentCommand command);

	void create(final CreateCommentCommand command);

	int qty(final long feedId);

	void remove(final long commentId);

	Comment modify(final ModifyCommentCommand command);

	CommentEntity search(final long commentId);
}
