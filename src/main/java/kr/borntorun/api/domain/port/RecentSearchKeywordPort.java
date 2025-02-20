package kr.borntorun.api.domain.port;

import java.util.List;

public interface RecentSearchKeywordPort {

	void removeAll(final long userId);

	void removeKeyword(final long userId, final String searchKeyword);

	void add(final long userId, final String searchKeyword);

	List<Object> search(final long userId);
}
