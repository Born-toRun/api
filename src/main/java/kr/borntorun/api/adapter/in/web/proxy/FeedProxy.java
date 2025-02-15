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
import kr.borntorun.api.domain.port.model.Feed;
import kr.borntorun.api.domain.port.model.FeedCard;
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
    return feedService.searchDetail(FeedConverter.INSTANCE.toSearchFeedDetailCommand(feedId, my));
  }

  @Cacheable(key = "#request == null ? 'searchAll: ' + #my.id + #pageable.pageSize : 'searchAll: ' + #my.id + #request.hashCode() + #pageable.pageSize")
  public Page<FeedCard> searchAll(final SearchFeedRequest request, final TokenDetail my, final int lastFeedId, final Pageable pageable) {
    return feedService.searchAll(FeedConverter.INSTANCE.toSearchAllFeedCommand(request, my, lastFeedId), pageable);
  }

  //  @DistributedLock(key = "'FeedView-'.concat(#id)", waitTime = 10L)
  public void increaseViewQty(final int feedId) {
    feedService.increaseViewQty(feedId);
  }

  @CacheEvict(allEntries = true)
  public void create(final CreateFeedRequest request, final TokenDetail my) {
    feedService.create(FeedConverter.INSTANCE.toCreateFeedCommand(request, my));
  }

  @CacheEvict(allEntries = true)
  public void remove(final int feedId, final TokenDetail my) {
    feedService.remove(FeedConverter.INSTANCE.toRemoveFeedCommand(feedId, my));
  }

  @CacheEvict(allEntries = true)
  public void modify(final ModifyFeedRequest request, final int feedId) {
    feedService.modify(FeedConverter.INSTANCE.toModifyFeedCommand(request, feedId));
  }
}
