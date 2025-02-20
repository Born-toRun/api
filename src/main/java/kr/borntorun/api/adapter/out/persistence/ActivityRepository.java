package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.ActivityEntity;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

	List<ActivityEntity> findAllByUserId(final long userId);
}
