package kr.borntorun.api.config.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.ModifyFeedRequest;
import kr.borntorun.api.adapter.out.persistence.FeedRepository;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.exception.ForBiddenException;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class FeedWriterValidationAspect {

	private final FeedRepository feedRepository;

	@Pointcut("execution(* kr.borntorun.api.adapter.in.web.FeedController.modify(..))")
	public void onModifyUser() {
	}

	@Pointcut("execution(* kr.borntorun.api.adapter.in.web.FeedController.remove(..))")
	public void onDeleteUser() {
	}

	@Before("onModifyUser() && args(my, feedId, request, ..)")
	public void validateModify(TokenDetail my, long feedId, ModifyFeedRequest request) throws
	  IllegalArgumentException {
		validate(my.getId(), feedId);
	}

	@Before("onDeleteUser() && args(my, feedId, ..)")
	public void validateDelete(TokenDetail my, long feedId) throws IllegalArgumentException {
		validate(my.getId(), feedId);
	}

	private void validate(long userId, long feedId) {
		if (!isValid(userId, feedId)) {
			throw new ForBiddenException("잘못된 접근입니다.");
		}
	}

	private boolean isValid(long userId, long feedId) {
		return feedRepository.findById(feedId)
		  .orElseThrow(() -> new NotFoundException("피드를 찾을 수 없습니다."))
		  .getUserId() == userId;
	}
}
