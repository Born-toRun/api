package kr.borntorun.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.entity.ActivityEntity;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer> {

  List<ActivityEntity> findAllByUserId(final int userId);
  Optional<ActivityEntity> findByIdAndIsDeletedFalse(int id);
}
