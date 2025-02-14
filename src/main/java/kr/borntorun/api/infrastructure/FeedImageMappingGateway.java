package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.FeedImageMappingRepository;
import kr.borntorun.api.domain.entity.FeedImageMappingEntity;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedImageMappingGateway {

  private final FeedImageMappingRepository feedImageMappingRepository;

  public void saveAll(final List<FeedImageMappingEntity> feedImageMappingEntities) {
    feedImageMappingRepository.saveAll(feedImageMappingEntities);
  }

  public List<FeedImageMappingEntity> searchByFeedIds(final List<Integer> feedIds) {
    return feedImageMappingRepository.findAllByFeedIdInAndIsDeletedFalse(feedIds);
  }

  public void removeAllByFeedId(final List<Integer> feedIds) {
    final List<FeedImageMappingEntity> feedImageMappingEntities = feedImageMappingRepository.findAllByFeedIdInAndIsDeletedFalse(feedIds);
    for (final FeedImageMappingEntity entity: feedImageMappingEntities) {
      entity.remove();
    }

    feedImageMappingRepository.saveAll(feedImageMappingEntities);
  }

  public void remove(Integer fileId) {
    final FeedImageMappingEntity feedImageMappingEntity = feedImageMappingRepository.findById(
            fileId)
        .orElseThrow(() -> new NotFoundException("파일을 찾을 수 없습니다."));

    feedImageMappingEntity.remove();
    feedImageMappingRepository.save(feedImageMappingEntity);
  }

  public void removeAllByFileId(List<Integer> imageIds) {
    final List<FeedImageMappingEntity> feedImageMappingEntities = feedImageMappingRepository.findAllByImageIdInAndIsDeletedFalse(imageIds);
    for (final FeedImageMappingEntity entity: feedImageMappingEntities) {
      entity.remove();
    }

    feedImageMappingRepository.saveAll(feedImageMappingEntities);
  }
}
