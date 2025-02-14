package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.entity.FeedImageMappingEntity;

public interface FeedImageMappingRepository extends JpaRepository<FeedImageMappingEntity, Integer> {

  List<FeedImageMappingEntity> findAllByFeedIdInAndIsDeletedFalse(final List<Integer> feedIds);
  List<FeedImageMappingEntity> findAllByImageIdInAndIsDeletedFalse(final List<Integer> imageIds);
}
