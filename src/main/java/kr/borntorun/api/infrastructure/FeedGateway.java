package kr.borntorun.api.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

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

	public Page<FeedEntity> searchAllByFilter(final SearchAllFeedQuery query, final Pageable pageable) {
		return feedQuery.searchAllByFilter(query, pageable);
	}

	public void increaseViewQty(final long feedId) {
		feedQuery.increaseViewQty(feedId);
	}

	public void create(final CreateFeedQuery query) {
		final FeedEntity feedEntity = feedConverter.toFeedEntity(query);
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

	public List<Long> removeAll(final long userId) {
		final List<FeedEntity> feeds = feedRepository.findAllByUserId(userId);
		final List<Long> feedIds = feeds.stream()
		  .map(FeedEntity::getId)
		  .collect(Collectors.toList());

		feedRepository.deleteAllById(feedIds);

		return feedIds;
	}

	public void remove(final long feedId) {
		feedRepository.deleteById(feedId);
	}

	public FeedEntity modify(final ModifyFeedQuery query) {
		final FeedEntity feedEntity = search(query.feedId());
		feedEntity.modify(query);

		feedEntity.add(query.imageIds().stream()
		  .map(imageId -> FeedImageMappingEntity.builder()
			.imageId(imageId)
			.build())
		  .collect(Collectors.toList()));

		return feedRepository.save(feedEntity);
	}

	public FeedEntity search(final long feedId) {
		return feedRepository.findById(feedId)
		  .orElseThrow(() -> new NotFoundException("해당 피드를 찾을 수 없습니다."));
	}
}
