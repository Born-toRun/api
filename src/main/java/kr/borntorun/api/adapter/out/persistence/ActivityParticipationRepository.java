package kr.borntorun.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.borntorun.api.domain.entity.ActivityParticipationEntity;

@Repository
public interface ActivityParticipationRepository extends JpaRepository<ActivityParticipationEntity, Long> {

	List<ActivityParticipationEntity> findAllByUserId(long userId);

	List<ActivityParticipationEntity> findAllByActivityId(long activityId);

	Optional<ActivityParticipationEntity> findByActivityIdAndUserId(long activityId, long userId);
}
