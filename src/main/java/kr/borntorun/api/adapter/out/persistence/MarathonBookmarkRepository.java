package kr.borntorun.api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.MarathonBookmarkEntity;


public interface MarathonBookmarkRepository extends JpaRepository<MarathonBookmarkEntity, Integer> {

  Optional<MarathonBookmarkEntity> findByUserIdAndMarathonId(int userId, long marathonId);
}
