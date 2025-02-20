package kr.borntorun.api.config.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.ModifyCommentRequest;
import kr.borntorun.api.adapter.out.persistence.CommentRepository;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.exception.ForBiddenException;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class CommentWriterValidationAspect {

	private final CommentRepository componentRepository;

	@Pointcut("execution(* kr.borntorun.api.adapter.in.web.CommentController.modify(..))")
	public void onModifyUser() {
	}

	@Pointcut("execution(* kr.borntorun.api.adapter.in.web.CommentController.remove(..))")
	public void onDeleteUser() {
	}

	@Before("onModifyUser() && args(my, commentId, request, ..)")
	public void validateModify(final TokenDetail my, final long commentId, final ModifyCommentRequest request) throws
	  IllegalArgumentException {
		validate(my.getId(), commentId);
	}

	@Before("onDeleteUser() && args(my, commentId, ..)")
	public void validateDelete(final TokenDetail my, final long commentId) throws IllegalArgumentException {
		validate(my.getId(), commentId);
	}

	private void validate(final long userId, final long feedId) {
		if (!isValid(userId, feedId)) {
			throw new ForBiddenException("잘못된 접근입니다.");
		}
	}

	private boolean isValid(final long userId, final long commentId) {
		return componentRepository.findById(commentId)
		  .orElseThrow(() -> new NotFoundException("댓글를 찾을 수 없습니다."))
		  .getUserId() == userId;
	}
}
