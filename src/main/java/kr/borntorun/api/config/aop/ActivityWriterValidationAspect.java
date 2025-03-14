package kr.borntorun.api.config.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.ModifyActivityRequest;
import kr.borntorun.api.adapter.out.persistence.ActivityRepository;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.exception.ForBiddenException;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ActivityWriterValidationAspect {

	private final ActivityRepository activityRepository;

	@Pointcut("execution(* kr.borntorun.api.adapter.in.web.ActivityController.open(..))")
	public void onOpenUser() {
	}

	@Pointcut("execution(* kr.borntorun.api.adapter.in.web.ActivityController.modify(..))")
	public void onModifyUser() {
	}

	@Pointcut("execution(* kr.borntorun.api.adapter.in.web.ActivityController.remove(..))")
	public void onDeleteUser() {
	}

	@Before("onOpenUser() && args(my, activityId, ..)")
	public void validateOpen(TokenDetail my, long activityId) throws IllegalArgumentException {
		validate(my.getId(), activityId);
	}

	@Before("onModifyUser() && args(my, activityId, request, ..)")
	public void validateModify(TokenDetail my, long activityId, ModifyActivityRequest request) throws
	  IllegalArgumentException {
		validate(my.getId(), activityId);
	}

	@Before("onDeleteUser() && args(my, activityId, ..)")
	public void validateDelete(TokenDetail my, long activityId) throws IllegalArgumentException {
		validate(my.getId(), activityId);
	}

	private void validate(long userId, long activityId) {
		if (!isValid(userId, activityId)) {
			throw new ForBiddenException("잘못된 접근입니다.");
		}
	}

	private boolean isValid(long userId, long activityId) {
		return activityRepository.findById(activityId)
		  .orElseThrow(() -> new NotFoundException("모임을 찾을 수 없습니다."))
		  .getUserId() == userId;
	}
}
