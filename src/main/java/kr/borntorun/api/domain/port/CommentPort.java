package kr.borntorun.api.domain.port;

import java.util.List;

import kr.borntorun.api.domain.entity.CommentEntity;
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.domain.port.model.CommentResult;
import kr.borntorun.api.domain.port.model.CreateCommentCommand;
import kr.borntorun.api.domain.port.model.DetailCommentCommand;
import kr.borntorun.api.domain.port.model.ModifyCommentCommand;
import kr.borntorun.api.domain.port.model.SearchAllCommentCommand;

public interface CommentPort {

	List<CommentResult> searchAll(SearchAllCommentCommand command);

	CommentDetail detail(DetailCommentCommand command);

	void create(CreateCommentCommand command);

	int qty(long feedId);

	void remove(long commentId);

	CommentResult modify(ModifyCommentCommand command);

	CommentEntity search(long commentId);
}
