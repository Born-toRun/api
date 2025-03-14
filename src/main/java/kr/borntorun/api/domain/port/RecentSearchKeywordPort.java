package kr.borntorun.api.domain.port;

import java.util.List;

public interface RecentSearchKeywordPort {

	void removeAll(long userId);

	void removeKeyword(long userId, String searchKeyword);

	void add(long userId, String searchKeyword);

	List<Object> search(long userId);
}
