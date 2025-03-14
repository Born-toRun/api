package kr.borntorun.api.core.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.adapter.out.thirdparty.RedisClient;
import kr.borntorun.api.domain.port.RecentSearchKeywordPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecentSearchKeywordService implements RecentSearchKeywordPort {

	private final String RECENT_SEARCH_KEYWORD_KEY_PREFIX = "recentSearch:";
	private final RedisClient redisClient;

	@Async
	@Transactional
	@Override
	public void removeAll(long userId) {
		redisClient.removeAll(RECENT_SEARCH_KEYWORD_KEY_PREFIX + userId);
	}

	@Async
	@Transactional
	@Override
	public void removeKeyword(long userId, String searchKeyword) {
		redisClient.removeValue(RECENT_SEARCH_KEYWORD_KEY_PREFIX + userId, searchKeyword);
	}

	@Async
	@Transactional
	@Override
	public void add(long userId, String searchKeyword) {
		final String key = RECENT_SEARCH_KEYWORD_KEY_PREFIX + userId;
		redisClient.add(key, searchKeyword);

		List<Object> recentSearchKeywords = redisClient.getList(key);

		int recentSearchKeywordMaxSize = 10;
		if (recentSearchKeywords.size() > recentSearchKeywordMaxSize) {
			recentSearchKeywords.remove(0);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Object> search(long userId) {
		return redisClient.getList(RECENT_SEARCH_KEYWORD_KEY_PREFIX + userId);
	}
}
