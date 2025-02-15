package kr.borntorun.api.core.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.MarathonConverter;
import kr.borntorun.api.domain.entity.MarathonEntity;
import kr.borntorun.api.domain.port.MarathonPort;
import kr.borntorun.api.domain.port.model.BookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.CancelBookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.domain.port.model.MarathonDetail;
import kr.borntorun.api.domain.port.model.SearchAllMarathonCommand;
import kr.borntorun.api.domain.port.model.SearchMarathonDetailCommand;
import kr.borntorun.api.infrastructure.MarathonGateway;
import kr.borntorun.api.infrastructure.model.BookmarkMarathonQuery;
import kr.borntorun.api.infrastructure.model.SearchMarathonQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MarathonService implements MarathonPort {

  private final MarathonGateway marathonGateway;

  @Transactional(readOnly = true)
  @Override
  public List<Marathon> search(final SearchAllMarathonCommand command) {
    SearchMarathonQuery query = MarathonConverter.INSTANCE.toSearchMarathonQuery(command);
    final List<MarathonEntity> marathons = marathonGateway.search(query);

    return MarathonConverter.INSTANCE.toMarathon(marathons, command.myUserId());
  }

  @Transactional(readOnly = true)
  @Override
  public MarathonDetail detail(final SearchMarathonDetailCommand command) {
    final MarathonEntity marathonEntity = marathonGateway.detail(command.marathonId());

    return MarathonConverter.INSTANCE.toMarathonDetail(marathonEntity, command.myUserId());
  }

  @Transactional
  @Override
  public long bookmark(final BookmarkMarathonCommand command) {
    BookmarkMarathonQuery query = MarathonConverter.INSTANCE.toBookmarkMarathonQuery(command);
    marathonGateway.bookmark(query);

    return command.marathonId();
  }

  @Transactional
  @Override
  public long cancelBookmark(final CancelBookmarkMarathonCommand command) {
    BookmarkMarathonQuery query = MarathonConverter.INSTANCE.toBookmarkMarathonQuery(command);
    marathonGateway.cancelBookmark(query);

    return command.marathonId();
  }
}
