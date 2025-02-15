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

  private final ActivityGateway activityGateway;

  @Transactional
  @Override
  public void create(final CreateActivityCommand command) {
    CreateActivityQuery query = ActivityConverter.INSTANCE.toCreateActivityQuery(command);
    activityGateway.create(query);
  }

  @Transactional
  @Override
  public void modify(final ModifyActivityCommand command) {
    ModifyActivityQuery query = ActivityConverter.INSTANCE.toModifyActivityQuery(command);
    activityGateway.modify(query);
  }

  @Transactional
  @Override
  public void removeAll(final int userId) {
    activityGateway.removeAll(userId);
  }

  @Transactional
  @Override
  public void remove(final int activityId) {
    activityGateway.remove(activityId);
  }

  @Transactional
  @Override
  public void participate(final ParticipateActivityCommand command) {
    ParticipateActivityQuery query = ActivityParticipationConverter.INSTANCE.toParticipateActivityQuery(command);
    activityGateway.participate(query);
  }

  @Transactional
  @Override
  public void participateCancel(final int participationId) {
    activityGateway.participateCancel(participationId);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Activity> searchAll(final SearchAllActivityCommand command) {
    SearchAllActivityQuery query = ActivityConverter.INSTANCE.toSearchAllActivityQuery(command);
    final List<ActivityEntity> activityEntities = activityGateway.searchAll(query);

    return ActivityConverter.INSTANCE.toActivityByUserId(activityEntities, command.myUserId());
  }

  @Transactional(readOnly = true)
  @Override
  public Activity search(final int activityId, final int myUserId) {
    ActivityEntity activityEntity = activityGateway.search(activityId);
    return ActivityConverter.INSTANCE.toActivityByUserId(activityEntity, myUserId);
  }

  @Transactional
  @Override
  public Activity open(final int activityId) {
    final ActivityEntity opened = activityGateway.open(activityId);
    int accessCode = activityGateway.initAccessCode(activityId);
    return ActivityConverter.INSTANCE.toActivity(opened, accessCode);
  }

  @Transactional
  @Override
  public void attendance(final AttendanceActivityCommand command) {
    AttendanceActivityQuery query = ActivityConverter.INSTANCE.toAttendanceActivityQuery(command);
    activityGateway.attendance(query);
  }

  @Transactional(readOnly = true)
  @Override
  public AttendanceResult getAttendance(final int activityId) {
    final List<ActivityParticipationEntity> activityParticipationEntities = activityGateway.searchParticipation(activityId);
    final List<UserEntity> participants = activityParticipationEntities.stream()
      .map(ActivityParticipationEntity::getUserEntity)
      .toList();

    final List<AttendanceResult.Person> participantDetails = participants.stream()
      .map(participant -> new AttendanceResult.Person(participant.getId(),
        participant.getName(),
        participant.getCrewEntity().getName(),
        participant.getProfileImageUri())
        ).toList();

    final UserEntity host = activityGateway.search(activityId)
      .getUserEntity();

    return new AttendanceResult(new AttendanceResult.Person(host.getId(),
        host.getName(),
        host.getCrewEntity().getName(),
        host.getProfileImageUri()
    ), participantDetails);
  }
}