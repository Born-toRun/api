package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.FeedImageMappingEntity;

public interface FeedImageMappingRepository extends JpaRepository<FeedImageMappingEntity, Long> {

	// List<FeedImageMappingEntity> findAllByFeedIdIn(final List<Long> feedIds);

	// List<FeedImageMappingEntity> findAllByImageIdIn(final List<Long> imageIds);

	void deleteAllByImageIdIn(List<Long> imageIds);
}
