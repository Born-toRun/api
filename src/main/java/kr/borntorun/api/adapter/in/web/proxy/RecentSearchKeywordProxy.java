package kr.borntorun.api.adapter.in.web.proxy;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.core.service.RecentSearchKeywordService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "recentSearchKeyword")
public class RecentSearchKeywordProxy {

	private final RecentSearchKeywordService recentSearchKeywordService;

	@CacheEvict(allEntries = true)
	public void removeAll(final long userId) {
		recentSearchKeywordService.removeAll(userId);
	}

	@CacheEvict(allEntries = true)
	public void removeKeyword(final long userId, final String searchKeyword) {
		recentSearchKeywordService.removeKeyword(userId, searchKeyword);
	}

	@CacheEvict(allEntries = true)
	public void add(final long userId, final String searchKeyword) {
		recentSearchKeywordService.add(userId, searchKeyword);
	}

	@Cacheable(key = "'search: ' + #userId")
	public List<Object> search(final long userId) {
		return recentSearchKeywordService.search(userId);
	}
}
