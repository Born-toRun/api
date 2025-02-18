package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.FeedImageMappingEntity;

public interface FeedImageMappingRepository extends JpaRepository<FeedImageMappingEntity, Integer> {

  List<FeedImageMappingEntity> findAllByFeedIdIn(final List<Integer> feedIds);
  List<FeedImageMappingEntity> findAllByImageIdIn(final List<Integer> imageIds);
  void deleteAllByImageIdIn(final List<Integer> imageIds);
}
