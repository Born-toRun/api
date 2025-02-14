package kr.borntorun.api.adapter.in.web.proxy;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.SearchMarathonRequest;
import kr.borntorun.api.core.converter.MarathonConverter;
import kr.borntorun.api.core.service.MarathonService;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.domain.port.model.MarathonDetail;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "marathon")
public class MarathonProxy {

  private final MarathonService marathonService;

  @Cacheable(key = "'search: ' + #request.hashCode() + #my.id")
  public List<Marathon> search(final SearchMarathonRequest request, final TokenDetail my) {
    return marathonService.search(MarathonConverter.INSTANCE.toSearchMarathonCommand(request, my.getId()));
  }

  @Cacheable(key = "'search: ' + #marathonId + #my.id")
  public MarathonDetail detail(final long marathonId, final TokenDetail my) {
    return marathonService.detail(MarathonConverter.INSTANCE.toSearchMarathonDetailCommand(marathonId, my.getId()));
  }

  @CacheEvict(allEntries = true)
  public long bookmark(final long marathonId, final TokenDetail my) {
    return marathonService.bookmark(MarathonConverter.INSTANCE.toBookmarkMarathonCommand(marathonId, my.getId()));
  }

  @CacheEvict(allEntries = true)
  public long cancelBookmark(final long marathonId, final TokenDetail my) {
    return marathonService.cancelBookmark(MarathonConverter.INSTANCE.toCancelBookmarkMarathonCommand(marathonId, my.getId()));
  }
}
