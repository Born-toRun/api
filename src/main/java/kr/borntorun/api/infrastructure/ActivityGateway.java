package kr.borntorun.api.infrastructure;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.ActivityParticipationRepository;
import kr.borntorun.api.adapter.out.persistence.ActivityRepository;
import kr.borntorun.api.adapter.out.persistence.querydsl.ActivityQuery;
import kr.borntorun.api.adapter.out.thirdparty.RedisClient;
import kr.borntorun.api.core.converter.ActivityConverter;
import kr.borntorun.api.core.converter.ActivityParticipationConverter;
import kr.borntorun.api.domain.entity.ActivityEntity;
import kr.borntorun.api.domain.entity.ActivityParticipationEntity;
import kr.borntorun.api.infrastructure.model.AttendanceActivityQuery;
import kr.borntorun.api.infrastructure.model.CreateActivityQuery;
import kr.borntorun.api.infrastructure.model.ModifyActivityQuery;
import kr.borntorun.api.infrastructure.model.ParticipateActivityQuery;
import kr.borntorun.api.infrastructure.model.SearchAllActivityQuery;
import kr.borntorun.api.support.exception.InvalidException;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ActivityGateway {

  private final String ACCESS_CODE_KEY_PREFIX = "accessCode";
  private final ActivityRepository activityRepository;
  private final ActivityParticipationRepository activityParticipationRepository;
  private final ActivityQuery activityQuery;
  private final RedisClient redisClient;

  public void create(final CreateActivityQuery createActivityQuery) {
    ActivityEntity activityEntity = ActivityConverter.INSTANCE.toActivityEntity(createActivityQuery);
    activityRepository.save(activityEntity);
  }

  public void modify(final ModifyActivityQuery modifyActivityQuery) {
    final ActivityEntity activityEntity = search(modifyActivityQuery.activityId());

    activityEntity.modify(modifyActivityQuery);
    activityRepository.save(activityEntity);
  }

  public void removeAll(final int userId) {
    activityParticipationRepository.deleteAllById(activityParticipationRepository.findAllByUserId(userId).stream()
        .map(ActivityParticipationEntity::getId)
        .collect(Collectors.toList()));

    activityRepository.deleteAllById(activityRepository.findAllByUserId(userId).stream()
        .map(ActivityEntity::getId)
        .collect(Collectors.toList()));
  }

  public void remove(final int activityId) {
    activityRepository.deleteById(activityId);
  }

  public void participate(final ParticipateActivityQuery participateActivityQuery) {
    ActivityParticipationEntity activityParticipationEntity = ActivityParticipationConverter.INSTANCE.toActivityParticipationEntity(participateActivityQuery);
    activityParticipationRepository.save(activityParticipationEntity);
  }

  public void participateCancel(final int participationId) {
    activityParticipationRepository.deleteById(participationId);
  }

  public List<ActivityEntity> searchAll(final SearchAllActivityQuery query) {
    final List<ActivityEntity> activityEntities = activityQuery.searchAllByFilter(query);

    if(null != query.recruitmentType()) {
      switch (query.recruitmentType()) {
        case RECRUITING -> {
          return activityEntities.stream()
              .filter(a -> a.getParticipantsLimit() > a.getActivityParticipationEntities().size())
              .toList();
        }
        case ALREADY_PARTICIPATING -> {
          return activityEntities.stream()
              .filter(a -> a.getActivityParticipationEntities().stream()
                  .anyMatch(ap -> ap.getUserEntity().getId() == query.myUserId()))
              .toList();
        }
        case ENDED -> {
          return activityEntities.stream()
              .filter(a -> a.getStartDate().isBefore(LocalDateTime.now()))
              .toList();
        }
        case CLOSED -> {
          return activityEntities.stream()
              .filter(a -> a.getParticipantsLimit() <= a.getActivityParticipationEntities().size())
              .toList();
        }
      }
    }

    return activityEntities;
  }

  public ActivityEntity search(int activityId) {
    return activityRepository.findById(activityId)
        .orElseThrow(() -> new NotFoundException("모임을 찾지 못했습니다."));
  }

  public ActivityEntity open(final int activityId) {
    final ActivityEntity activity = search(activityId);

    final LocalDateTime now = LocalDateTime.now();

    if(now.isAfter(activity.getStartDate().minusMinutes(10L)) && now.isBefore(activity.getStartDate().plusMonths(10L))) {
      activity.open();
      return activityRepository.save(activity);
    }

    throw new InvalidException("모임 일정 외에는 오픈할 수 없습니다.");
  }

  public void attendance(final AttendanceActivityQuery attendanceActivityQuery) {
    if (!redisClient.exist(ACCESS_CODE_KEY_PREFIX + attendanceActivityQuery.activityId())) {
      throw new InvalidException("참여코드가 만료되었습니다.");
    }

    if ((int) redisClient.get(ACCESS_CODE_KEY_PREFIX + attendanceActivityQuery.activityId()) == attendanceActivityQuery.accessCode()) {
      final ActivityParticipationEntity activityParticipation = activityParticipationRepository.findByActivityIdAndUserId(attendanceActivityQuery.activityId(), attendanceActivityQuery.myUserId())
          .orElseThrow(() -> new NotFoundException("참여의사를 밝히지 않은 모임에 출석할 수 없습니다."));
      activityParticipation.attendance();
      activityParticipationRepository.save(activityParticipation);
    } else {
      throw new InvalidException("참여코드가 일치하지 않습니다.");
    }
  }

  public int initAccessCode(final int activityId) {
    final int accessCode = new Random().nextInt(100) + 1;
    redisClient.save(ACCESS_CODE_KEY_PREFIX + activityId, accessCode, Duration.ofMinutes(5));

    return accessCode;
  }

  public List<ActivityParticipationEntity> searchParticipation(final int activityId) {
    return activityParticipationRepository.findAllByActivityId(activityId);
  }
}
