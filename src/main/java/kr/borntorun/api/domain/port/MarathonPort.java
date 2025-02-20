package kr.borntorun.api.domain.port;

import java.util.List;

import kr.borntorun.api.domain.port.model.BookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.CancelBookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.domain.port.model.MarathonDetail;
import kr.borntorun.api.domain.port.model.SearchAllMarathonCommand;
import kr.borntorun.api.domain.port.model.SearchMarathonDetailCommand;

public interface MarathonPort {

	List<Marathon> search(final SearchAllMarathonCommand command);

	MarathonDetail detail(final SearchMarathonDetailCommand command);

	long bookmark(final BookmarkMarathonCommand command);

	long cancelBookmark(final CancelBookmarkMarathonCommand command);
}
