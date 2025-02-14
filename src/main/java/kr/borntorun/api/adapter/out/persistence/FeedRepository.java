package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.entity.FeedEntity;

public interface FeedRepository extends JpaRepository<FeedEntity, Integer> {

  List<FeedEntity> findAllByUserId(final int userId);
}
