package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.FeedImageMappingRepository;
import kr.borntorun.api.adapter.out.persistence.FeedRepository;
import kr.borntorun.api.adapter.out.persistence.querydsl.FeedQuery;
import kr.borntorun.api.core.converter.FeedConverter;
import kr.borntorun.api.domain.entity.FeedEntity;
import kr.borntorun.api.domain.entity.FeedImageMappingEntity;
import kr.borntorun.api.infrastructure.model.CreateFeedQuery;
import kr.borntorun.api.infrastructure.model.ModifyFeedQuery;
import kr.borntorun.api.infrastructure.model.SearchAllFeedQuery;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedGateway {

	private final FeedConverter feedConverter;
	private final FeedRepository feedRepository;
	private final FeedImageMappingRepository feedImageMappingRepository;
	private final FeedQuery feedQuery;

	public Page<FeedEntity> searchAllByFilter(SearchAllFeedQuery query, Pageable pageable) {
		return feedQuery.searchAllByFilter(query, pageable);
	}

	public void increaseViewQty(long feedId) {
		feedQuery.increaseViewQty(feedId);
	}

	public void create(CreateFeedQuery query) {
		FeedEntity feedEntity = feedConverter.toFeedEntity(query);
		feedRepository.save(feedEntity);

		List<FeedImageMappingEntity> feedImageMappingEntities = query.imageIds().stream()
		  .map(imageId -> FeedImageMappingEntity.builder()
			.imageId(imageId)
			.feedId(feedEntity.getId())
			.build())
		  .toList();

		feedEntity.add(feedImageMappingEntities);

		feedImageMappingRepository.saveAll(feedImageMappingEntities);
	}

	public void remove(long feedId) {
		feedRepository.deleteById(feedId);
	}

	public FeedEntity modify(ModifyFeedQuery query) {
		FeedEntity feedEntity = search(query.feedId());

		List<FeedImageMappingEntity> feedImageMappingEntities = query.imageIds().stream()
		  .map(imageId -> FeedImageMappingEntity.builder()
			.imageId(imageId)
			.build())
		  .toList();

		feedEntity.modify(query);
		feedEntity.modify(feedImageMappingEntities);
		feedImageMappingRepository.saveAll(feedImageMappingEntities);

		return feedRepository.save(feedEntity);
	}

	public FeedEntity search(long feedId) {
		return feedRepository.findById(feedId)
		  .orElseThrow(() -> new NotFoundException("해당 피드를 찾을 수 없습니다."));
	}
}
