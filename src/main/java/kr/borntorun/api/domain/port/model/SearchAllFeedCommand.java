package kr.borntorun.api.domain.port.model;

import kr.borntorun.api.domain.constant.FeedCategory;
import kr.borntorun.api.support.TokenDetail;

public record SearchAllFeedCommand(FeedCategory category,
								   String searchKeyword,
								   boolean isMyCrew,
								   TokenDetail my,
								   long lastFeedId) {

}
