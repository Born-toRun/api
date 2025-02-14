package kr.borntorun.api.domain.port;

import java.util.List;

public interface RecentSearchKeywordPort {

  void removeAll(final int userId);
  void removeKeyword(final int userId, final String searchKeyword);
  void add(final int userId, final String searchKeyword);
  List<Object> search(final int userId);
}
