package kr.borntorun.api.adapter.in.web.proxy;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.AttendanceActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.CreateActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchAllActivityRequest;
import kr.borntorun.api.core.converter.ActivityConverter;
import kr.borntorun.api.core.service.ActivityService;
import kr.borntorun.api.domain.port.model.Activity;
import kr.borntorun.api.domain.port.model.AttendanceActivityCommand;
import kr.borntorun.api.domain.port.model.AttendanceResult;
import kr.borntorun.api.domain.port.model.CreateActivityCommand;
import kr.borntorun.api.domain.port.model.ModifyActivityCommand;
import kr.borntorun.api.domain.port.model.ParticipateActivityCommand;
import kr.borntorun.api.domain.port.model.SearchAllActivityCommand;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "activity")
public class ActivityProxy {

	private final ActivityConverter activityConverter;

	private final ActivityService activityService;

	@CacheEvict(allEntries = true)
	public void create(TokenDetail my, CreateActivityRequest request) {
		CreateActivityCommand command = activityConverter.toCreateActivityCommand(request, my);
		activityService.create(command);
	}

	@CacheEvict(allEntries = true)
	public void modify(ModifyActivityRequest request, long activityId) {
		ModifyActivityCommand command = activityConverter.toModifyActivityCommand(request, activityId);
		activityService.modify(command);
	}

	@CacheEvict(allEntries = true)
	public void remove(long activityId) {
		activityService.remove(activityId);
	}

	@CacheEvict(allEntries = true)
	public void participate(long activityId, long myUserId) {
		ParticipateActivityCommand command = new ParticipateActivityCommand(activityId, myUserId);
		activityService.participate(command);
	}

	@CacheEvict(allEntries = true)
	public void participateCancel(long participationId) {
		activityService.participateCancel(participationId);
	}

	@Cacheable(key = "'searchAll: ' + #my.id + #request.hashCode()")
	public List<Activity> searchAll(SearchAllActivityRequest request, TokenDetail my) {
		SearchAllActivityCommand command = activityConverter.toSearchAllActivityCommand(request, my);
		return activityService.searchAll(command);
	}

	@Cacheable(key = "'search: ' + #my.id + #activityId")
	public Activity search(long activityId, TokenDetail my) {
		return activityService.search(activityId, my.getId());
	}

	@CacheEvict(allEntries = true)
	public Activity open(long activityId) {
		return activityService.open(activityId);
	}

	@CacheEvict(allEntries = true)
	public void attendance(AttendanceActivityRequest request, long activityId, long myUserId) {
		AttendanceActivityCommand command = activityConverter.toAttendanceActivityCommand(request, activityId,
		  myUserId);
		activityService.attendance(command);
	}

	@Cacheable(key = "'getAttendance: ' + #activityId")
	public AttendanceResult getAttendance(long activityId) {
		return activityService.getAttendance(activityId);
	}
}
