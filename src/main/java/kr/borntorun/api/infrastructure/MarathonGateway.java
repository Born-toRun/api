package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.MarathonBookmarkRepository;
import kr.borntorun.api.adapter.out.persistence.MarathonRepository;
import kr.borntorun.api.core.converter.MarathonBookmarkConverter;
import kr.borntorun.api.domain.entity.MarathonBookmarkEntity;
import kr.borntorun.api.domain.entity.MarathonEntity;
import kr.borntorun.api.infrastructure.model.BookmarkMarathonQuery;
import kr.borntorun.api.infrastructure.model.SearchMarathonQuery;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MarathonGateway {

  private final MarathonRepository marathonRepository;
  private final MarathonBookmarkRepository marathonBookmarkRepository;

  public List<MarathonEntity> search(SearchMarathonQuery query) {
    return marathonRepository.findAllByLocationInAndCourseInAndIsDeletedFalse(query.locations(), query.courses());
  }

  public MarathonEntity detail(long marathonId) {
    return marathonRepository.findByIdAndIsDeletedFalse(marathonId);
  }

  public void bookmark(BookmarkMarathonQuery query) {
    final MarathonBookmarkEntity marathonBookmarkEntity = marathonBookmarkRepository.findByUserIdAndMarathonId(query.getMyUserId(), query.getMarathonId())
        .orElse(MarathonBookmarkConverter.INSTANCE.toMarathonBookmarkEntity(query, false));
    marathonBookmarkEntity.setIsDeleted(false);
    marathonBookmarkRepository.save(marathonBookmarkEntity);
  }

  public void cancelBookmark(BookmarkMarathonQuery query) {
    final MarathonBookmarkEntity marathonBookmarkEntity = marathonBookmarkRepository.findByUserIdAndMarathonIdAndIsDeletedFalse(query.getMyUserId(), query.getMarathonId())
        .orElseThrow(() -> new NotFoundException("북마크가 되지 않았거나 이미 취소되었습니다."));
    marathonBookmarkEntity.setIsDeleted(true);
    marathonBookmarkRepository.save(marathonBookmarkEntity);
  }
}
