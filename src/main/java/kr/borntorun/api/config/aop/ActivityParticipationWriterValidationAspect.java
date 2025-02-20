package kr.borntorun.api.config.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.ActivityParticipationRepository;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.exception.ForBiddenException;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ActivityParticipationWriterValidationAspect {

	private final ActivityParticipationRepository activityParticipationRepository;

	@Pointcut("execution(* kr.borntorun.api.adapter.in.web.ActivityController.participateCancel(..))")
	public void onCancelUser() {
	}

	@Before("onCancelUser() && args(my, participationId, ..)")
	public void validateCancel(final TokenDetail my, final long participationId) throws IllegalArgumentException {
		validate(my.getId(), participationId);
	}

	private void validate(final long userId, final long participationId) {
		if (!isValid(userId, participationId)) {
			throw new ForBiddenException("잘못된 접근입니다.");
		}
	}

	private boolean isValid(final long userId, final long participationId) {
		return activityParticipationRepository.findById(participationId)
		  .orElseThrow(() -> new NotFoundException("참여를 하지 않은 상태입니다."))
		  .getUserId() == userId;
	}
}
