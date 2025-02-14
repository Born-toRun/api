package kr.borntorun.api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.entity.MarathonBookmarkEntity;


public interface MarathonBookmarkRepository extends JpaRepository<MarathonBookmarkEntity, Integer> {

  Optional<MarathonBookmarkEntity> findByUserIdAndMarathonIdAndIsDeletedFalse(int userId, long marathonId);
  Optional<MarathonBookmarkEntity> findByUserIdAndMarathonId(int userId, long marathonId);
}
