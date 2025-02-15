package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.CreateFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchFeedRequest;
import kr.borntorun.api.core.converter.FeedConverter;
import kr.borntorun.api.core.service.FeedService;
import kr.borntorun.api.domain.port.model.CreateFeedCommand;
import kr.borntorun.api.domain.port.model.Feed;
import kr.borntorun.api.domain.port.model.FeedCard;
import kr.borntorun.api.domain.port.model.ModifyFeedCommand;
import kr.borntorun.api.domain.port.model.RemoveFeedCommand;
import kr.borntorun.api.domain.port.model.SearchAllFeedCommand;
import kr.borntorun.api.domain.port.model.SearchFeedDetailCommand;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "feed")
@Slf4j
public class FeedProxy {

  private final FeedService feedService;

  @Cacheable(key = "'searchDetail: ' + #feedId + #my.id")
  public Feed searchDetail(final TokenDetail my, final int feedId) {
    SearchFeedDetailCommand command = FeedConverter.INSTANCE.toSearchFeedDetailCommand(feedId, my);
    return feedService.searchDetail(command);
  }

  @Cacheable(key = "#request == null ? 'searchAll: ' + #my.id + #pageable.pageSize : 'searchAll: ' + #my.id + #request.hashCode() + #pageable.pageSize")
  public Page<FeedCard> searchAll(final SearchFeedRequest request, final TokenDetail my, final int lastFeedId, final Pageable pageable) {
    SearchAllFeedCommand command = FeedConverter.INSTANCE.toSearchAllFeedCommand(request, my, lastFeedId);
    return feedService.searchAll(command, pageable);
  }

  //  @DistributedLock(key = "'FeedView-'.concat(#id)", waitTime = 10L)
  public void increaseViewQty(final int feedId) {
    feedService.increaseViewQty(feedId);
  }

  @CacheEvict(allEntries = true)
  public void create(final CreateFeedRequest request, final TokenDetail my) {
    CreateFeedCommand command = FeedConverter.INSTANCE.toCreateFeedCommand(request, my);
    feedService.create(command);
  }

  @CacheEvict(allEntries = true)
  public void remove(final int feedId, final TokenDetail my) {
    RemoveFeedCommand command = FeedConverter.INSTANCE.toRemoveFeedCommand(feedId, my);
    feedService.remove(command);
  }

  @CacheEvict(allEntries = true)
  public void modify(final ModifyFeedRequest request, final int feedId) {
    ModifyFeedCommand command = FeedConverter.INSTANCE.toModifyFeedCommand(request, feedId);
    feedService.modify(command);
  }
}
