package kr.borntorun.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.ActivityParticipationEntity;

public interface ActivityParticipationRepository extends JpaRepository<ActivityParticipationEntity, Long> {

  List<ActivityParticipationEntity> findAllByUserId(final long userId);
  List<ActivityParticipationEntity> findAllByActivityId(final long activityId);
  Optional<ActivityParticipationEntity> findByActivityIdAndUserId(final long activityId, final long userId);
}
