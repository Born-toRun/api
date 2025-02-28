package kr.borntorun.api.core.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.FeedConverter;
import kr.borntorun.api.domain.entity.FeedEntity;
import kr.borntorun.api.domain.entity.FeedImageMappingEntity;
import kr.borntorun.api.domain.entity.UserEntity;
import kr.borntorun.api.domain.port.FeedPort;
import kr.borntorun.api.domain.port.model.CreateFeedCommand;
import kr.borntorun.api.domain.port.model.FeedCard;
import kr.borntorun.api.domain.port.model.FeedResult;
import kr.borntorun.api.domain.port.model.ModifyFeedCommand;
import kr.borntorun.api.domain.port.model.RemoveFeedCommand;
import kr.borntorun.api.domain.port.model.SearchAllFeedCommand;
import kr.borntorun.api.domain.port.model.SearchFeedDetailCommand;
import kr.borntorun.api.infrastructure.FeedGateway;
import kr.borntorun.api.infrastructure.FeedImageMappingGateway;
import kr.borntorun.api.infrastructure.UserGateway;
import kr.borntorun.api.infrastructure.model.CreateFeedQuery;
import kr.borntorun.api.infrastructure.model.ModifyFeedQuery;
import kr.borntorun.api.infrastructure.model.SearchAllFeedQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedService implements FeedPort {

	private final FeedConverter feedConverter;
	private final FeedGateway feedGateway;
	private final FeedImageMappingGateway feedImageMappingGateway;
	private final UserGateway userGateway;

	@Transactional(readOnly = true)
	@Override
	public FeedResult searchDetail(final SearchFeedDetailCommand command) {
		final FeedEntity feedEntity = feedGateway.search(command.feedId());
		return feedConverter.toFeed(feedEntity, command.my());
	}

	@Transactional(readOnly = true)
	@Override
	public Page<FeedCard> searchAll(final SearchAllFeedCommand command, final Pageable pageable) {
		List<Long> searchedUserIds = Optional.ofNullable(command.searchKeyword())
		  .map(userGateway::searchByUserName)
		  .map(users -> users.stream()
			.map(UserEntity::getId)
			.toList())
		  .orElseGet(Collections::emptyList);

		SearchAllFeedQuery query = feedConverter.toSearchAllFeedQuery(command, searchedUserIds);
		Page<FeedEntity> feedPage = feedGateway.searchAllByFilter(query, pageable);

		return feedPage.map(entity -> feedConverter.toFeedCard(entity,
		  entity.hasMyComment(command.my().getId()),
		  entity.hasMyRecommendation(command.my().getId())));
	}

	@Async
	@Override
	@Transactional
	public void increaseViewQty(final long feedId) {
		feedGateway.increaseViewQty(feedId);
	}

	@Transactional
	@Override
	public void create(final CreateFeedCommand command) {
		CreateFeedQuery query = feedConverter.toCreateFeedQuery(command);
		feedGateway.create(query);
	}

	@Transactional
	@Override
	public void remove(final RemoveFeedCommand command) {
		feedGateway.remove(command.feedId());
	}

	@Transactional
	@Override
	public void modify(final ModifyFeedCommand command) {
		ModifyFeedQuery query = feedConverter.toModifyFeedQuery(command);
		final FeedEntity modified = feedGateway.modify(query);

		final List<Long> removedImageIds = modified.getFeedImageMappingEntities().stream()
		  .map(FeedImageMappingEntity::getImageId)
		  .filter(imageId -> !command.imageIds().contains(imageId))
		  .toList();

		feedImageMappingGateway.removeAllByFileId(removedImageIds);
	}
}
