package kr.borntorun.api.domain.port;

import java.util.List;

import kr.borntorun.api.domain.port.model.BookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.CancelBookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.domain.port.model.MarathonDetail;
import kr.borntorun.api.domain.port.model.SearchAllMarathonCommand;
import kr.borntorun.api.domain.port.model.SearchMarathonDetailCommand;

public interface MarathonPort {

	List<Marathon> search(SearchAllMarathonCommand command);

	MarathonDetail detail(SearchMarathonDetailCommand command);

	long bookmark(BookmarkMarathonCommand command);

	long cancelBookmark(CancelBookmarkMarathonCommand command);
}
