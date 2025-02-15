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

  private final ActivityService activityService;

  @CacheEvict(allEntries = true)
  public void create(final TokenDetail my, final CreateActivityRequest request) {
    CreateActivityCommand command = ActivityConverter.INSTANCE.toCreateActivityCommand(request, my);
    activityService.create(command);
  }

  @CacheEvict(allEntries = true)
  public void modify(final ModifyActivityRequest request, final int activityId) {
    ModifyActivityCommand command = ActivityConverter.INSTANCE.toModifyActivityCommand(request, activityId);
    activityService.modify(command);
  }

  @CacheEvict(allEntries = true)
  public void remove(final int activityId) {
    activityService.remove(activityId);
  }

  @CacheEvict(allEntries = true)
  public void participate(final int activityId, final int myUserId) {
    ParticipateActivityCommand command = new ParticipateActivityCommand(activityId, myUserId);
    activityService.participate(command);
  }

  @CacheEvict(allEntries = true)
  public void participateCancel(final int participationId) {
    activityService.participateCancel(participationId);
  }

  @Cacheable(key = "'searchAll: ' + #my.id + #request.hashCode()")
  public List<Activity> searchAll(final SearchAllActivityRequest request, final TokenDetail my) {
    SearchAllActivityCommand command = ActivityConverter.INSTANCE.toSearchAllActivityCommand(request, my);
    return activityService.searchAll(command);
  }

  @Cacheable(key = "'search: ' + #my.id + #activityId")
  public Activity search(final int activityId, final TokenDetail my) {
    return activityService.search(activityId, my.getId());
  }

  @CacheEvict(allEntries = true)
  public Activity open(final int activityId) {
    return activityService.open(activityId);
  }

  @CacheEvict(allEntries = true)
  public void attendance(final AttendanceActivityRequest request, final int activityId, final int myUserId) {
    AttendanceActivityCommand command = ActivityConverter.INSTANCE.toAttendanceActivityCommand(request, activityId, myUserId);
    activityService.attendance(command);
  }

  @Cacheable(key = "'getAttendance: ' + #activityId")
  public AttendanceResult getAttendance(final int activityId) {
    return activityService.getAttendance(activityId);
  }
}
