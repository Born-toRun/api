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

	FeedResult searchDetail(final SearchFeedDetailCommand command);

	Page<FeedCard> searchAll(final SearchAllFeedCommand command, final Pageable pageable);

	void increaseViewQty(final long feedId);

	void create(final CreateFeedCommand command);

	void remove(final RemoveFeedCommand command);

	void modify(final ModifyFeedCommand command);
}
