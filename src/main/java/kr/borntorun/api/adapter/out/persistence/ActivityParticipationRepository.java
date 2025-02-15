package kr.borntorun.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.ActivityParticipationEntity;

public interface ActivityParticipationRepository extends JpaRepository<ActivityParticipationEntity, Integer> {

  List<ActivityParticipationEntity> findAllByUserId(final int userId);
  List<ActivityParticipationEntity> findAllByActivityId(final int activityId);
  Optional<ActivityParticipationEntity> findByActivityIdAndUserId(final int activityId, final int userId);
}
