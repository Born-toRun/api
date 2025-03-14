package kr.borntorun.api.adapter.in.web.proxy;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.SearchAllMarathonRequest;
import kr.borntorun.api.core.converter.MarathonConverter;
import kr.borntorun.api.core.service.MarathonService;
import kr.borntorun.api.domain.port.model.BookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.CancelBookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.domain.port.model.MarathonDetail;
import kr.borntorun.api.domain.port.model.SearchAllMarathonCommand;
import kr.borntorun.api.domain.port.model.SearchMarathonDetailCommand;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "marathon")
public class MarathonProxy {

	private final MarathonConverter marathonConverter;

	private final MarathonService marathonService;

	@Cacheable(key = "'searchAll: ' + #request.hashCode() + #my.id")
	public List<Marathon> search(SearchAllMarathonRequest request, TokenDetail my) {
		SearchAllMarathonCommand command = marathonConverter.toSearchAllMarathonCommand(request, my.getId());
		return marathonService.search(command);
	}

	@Cacheable(key = "'search: ' + #marathonId + #my.id")
	public MarathonDetail detail(long marathonId, TokenDetail my) {
		SearchMarathonDetailCommand command = marathonConverter.toSearchMarathonDetailCommand(marathonId, my.getId());
		return marathonService.detail(command);
	}

	@CacheEvict(allEntries = true)
	public long bookmark(long marathonId, TokenDetail my) {
		BookmarkMarathonCommand command = marathonConverter.toBookmarkMarathonCommand(marathonId, my.getId());
		return marathonService.bookmark(command);
	}

	@CacheEvict(allEntries = true)
	public long cancelBookmark(long marathonId, TokenDetail my) {
		CancelBookmarkMarathonCommand command = marathonConverter.toCancelBookmarkMarathonCommand(marathonId,
		  my.getId());
		return marathonService.cancelBookmark(command);
	}
}
