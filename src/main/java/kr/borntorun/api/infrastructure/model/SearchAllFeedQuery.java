package kr.borntorun.api.infrastructure.model;

import java.util.List;

import kr.borntorun.api.domain.constant.FeedCategory;
import kr.borntorun.api.support.TokenDetail;

public record SearchAllFeedQuery(FeedCategory category,
                                 String searchKeyword,
                                 boolean isMyCrew,
                                 TokenDetail my,
                                 long lastFeedId,
                                 List<Long> searchedUserIds) {

  public boolean isUsedIntegratedSearch() {
    return null != searchKeyword && !searchKeyword.isEmpty();
  }
}
