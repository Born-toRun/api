package kr.borntorun.api.core.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.ActivityConverter;
import kr.borntorun.api.core.converter.ActivityParticipationConverter;
import kr.borntorun.api.domain.entity.ActivityEntity;
import kr.borntorun.api.domain.entity.ActivityParticipationEntity;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.ActivityPort;
import kr.borntorun.api.domain.port.model.Activity;
import kr.borntorun.api.domain.port.model.AttendanceActivityCommand;
import kr.borntorun.api.domain.port.model.AttendanceResult;
import kr.borntorun.api.domain.port.model.CreateActivityCommand;
import kr.borntorun.api.domain.port.model.ModifyActivityCommand;
import kr.borntorun.api.domain.port.model.ParticipateActivityCommand;
import kr.borntorun.api.domain.port.model.SearchAllActivityCommand;
import kr.borntorun.api.infrastructure.ActivityGateway;
import kr.borntorun.api.infrastructure.model.AttendanceActivityQuery;
import kr.borntorun.api.infrastructure.model.CreateActivityQuery;
import kr.borntorun.api.infrastructure.model.ModifyActivityQuery;
import kr.borntorun.api.infrastructure.model.ParticipateActivityQuery;
import kr.borntorun.api.infrastructure.model.SearchAllActivityQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ActivityService implements ActivityPort {

	private final ActivityParticipationConverter activityParticipationConverter;

	private final ActivityConverter activityConverter;

	private final ActivityGateway activityGateway;

	@Transactional
	@Override
	public void create(CreateActivityCommand command) {
		CreateActivityQuery query = activityConverter.toCreateActivityQuery(command);
		activityGateway.create(query);
	}

	@Transactional
	@Override
	public void modify(ModifyActivityCommand command) {
		ModifyActivityQuery query = activityConverter.toModifyActivityQuery(command);
		activityGateway.modify(query);
	}

	@Transactional
	@Override
	public void removeAll(long userId) {
		activityGateway.removeAll(userId);
	}

	@Transactional
	@Override
	public void remove(long activityId) {
		activityGateway.remove(activityId);
	}

	@Transactional
	@Override
	public void participate(ParticipateActivityCommand command) {
		ParticipateActivityQuery query = activityParticipationConverter.toParticipateActivityQuery(command);
		activityGateway.participate(query);
	}

	@Transactional
	@Override
	public void participateCancel(long participationId) {
		activityGateway.participateCancel(participationId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Activity> searchAll(SearchAllActivityCommand command) {
		SearchAllActivityQuery query = activityConverter.toSearchAllActivityQuery(command);
		List<ActivityEntity> activityEntities = activityGateway.searchAll(query);

		return activityConverter.toActivityByUserId(activityEntities, command.myUserId());
	}

	@Transactional(readOnly = true)
	@Override
	public Activity search(long activityId, long myUserId) {
		ActivityEntity activityEntity = activityGateway.search(activityId);
		return activityConverter.toActivityByUserId(activityEntity, myUserId);
	}

	@Transactional
	@Override
	public Activity open(long activityId) {
		ActivityEntity opened = activityGateway.open(activityId);
		int accessCode = activityGateway.initAccessCode(activityId);
		return activityConverter.toActivity(opened, accessCode);
	}

	@Transactional
	@Override
	public void attendance(AttendanceActivityCommand command) {
		AttendanceActivityQuery query = activityConverter.toAttendanceActivityQuery(command);
		activityGateway.attendance(query);
	}

	@Transactional(readOnly = true)
	@Override
	public AttendanceResult getAttendance(long activityId) {
		List<ActivityParticipationEntity> activityParticipationEntities = activityGateway.searchParticipation(
		  activityId);
		List<UserEntity> participants = activityParticipationEntities.stream()
		  .map(ActivityParticipationEntity::getUserEntity)
		  .toList();
		UserEntity host = activityGateway.search(activityId)
		  .getUserEntity();

		return activityParticipationConverter.toAttendanceResult(host, participants);
	}
}