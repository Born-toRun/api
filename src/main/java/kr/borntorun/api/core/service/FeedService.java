package kr.borntorun.api.core.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import kr.borntorun.api.domain.port.model.Feed;
import kr.borntorun.api.domain.port.model.FeedCard;
import kr.borntorun.api.domain.port.model.ModifyFeedCommand;
import kr.borntorun.api.domain.port.model.RemoveFeedCommand;
import kr.borntorun.api.domain.port.model.SearchAllFeedCommand;
import kr.borntorun.api.domain.port.model.SearchFeedDetailCommand;
import kr.borntorun.api.infrastructure.CommentGateway;
import kr.borntorun.api.infrastructure.FeedGateway;
import kr.borntorun.api.infrastructure.FeedImageMappingGateway;
import kr.borntorun.api.infrastructure.ObjectStorageGateway;
import kr.borntorun.api.infrastructure.UserGateway;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class FeedService implements FeedPort {

  private final FeedGateway feedGateway;
  private final FeedImageMappingGateway feedImageMappingGateway;
  private final UserGateway userGateway;
  private final CommentGateway commentGateway;
  private final ObjectStorageGateway objectStorageGateway;

  @Transactional(readOnly = true)
  @Override
  public Feed searchDetail(final SearchFeedDetailCommand command) {
    final FeedEntity feedEntity = feedGateway.search(command.feedId());
    return FeedConverter.INSTANCE.toFeed(feedEntity, command.my());
  }

  @Transactional(readOnly = true)
  @Override
  public Page<FeedCard> searchAll(final SearchAllFeedCommand command, final Pageable pageable) {
    List<Integer> searchedUserIds = Collections.emptyList();
    if(Objects.nonNull(command.searchKeyword())) {
      searchedUserIds = userGateway.searchByUserName(command.searchKeyword()).stream()
          .map(UserEntity::getId)
          .collect(Collectors.toList());
    }

    final Page<FeedEntity> feedPage = feedGateway.searchAllByFilter(FeedConverter.INSTANCE.toSearchAllFeedQuery(command, searchedUserIds), pageable);

    if(feedPage.isEmpty()) {
      return Page.empty();
    }

    return feedPage.map(entity -> FeedConverter.INSTANCE.toFeedCard(entity,
        entity.hasComment(command.my().getId()),
        entity.hasRecommendation(command.my().getId())));
  }

  @Async
  @Override
  public void increaseViewQty(final int feedId) {
    feedGateway.increaseViewQty(feedId);
  }

  @Transactional
  @Override
  public void create(final CreateFeedCommand command) {
    feedGateway.create(FeedConverter.INSTANCE.toCreateFeedQuery(command));
  }

  @Transactional
  @Override
  public void remove(final RemoveFeedCommand command) {
    feedGateway.remove(command.feedId());
  }

  @Transactional
  @Override
  public void modify(final ModifyFeedCommand command) {
    final FeedEntity modified = feedGateway.modify(FeedConverter.INSTANCE.toModifyFeedQuery(command));

    final List<Integer> removedImageIds = modified.getFeedImageMappingEntities().stream()
        .map(FeedImageMappingEntity::getImageId)
        .filter(imageId -> !command.imageIds().contains(imageId))
        .toList();

    feedImageMappingGateway.removeAllByFileId(removedImageIds);
  }
}
