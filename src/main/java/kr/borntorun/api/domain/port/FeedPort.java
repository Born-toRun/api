package kr.borntorun.api.domain.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.borntorun.api.domain.port.model.CreateFeedCommand;
import kr.borntorun.api.domain.port.model.FeedCard;
import kr.borntorun.api.domain.port.model.FeedResult;
import kr.borntorun.api.domain.port.model.ModifyFeedCommand;
import kr.borntorun.api.domain.port.model.RemoveFeedCommand;
import kr.borntorun.api.domain.port.model.SearchAllFeedCommand;
import kr.borntorun.api.domain.port.model.SearchFeedDetailCommand;

public interface FeedPort {

	FeedResult searchDetail(SearchFeedDetailCommand command);

	Page<FeedCard> searchAll(SearchAllFeedCommand command, Pageable pageable);

	void increaseViewQty(long feedId);

	void create(CreateFeedCommand command);

	void remove(RemoveFeedCommand command);

	void modify(ModifyFeedCommand command);
}
