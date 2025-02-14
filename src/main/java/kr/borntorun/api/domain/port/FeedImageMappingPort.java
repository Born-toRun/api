package kr.borntorun.api.domain.port;

import java.util.List;

import kr.borntorun.api.domain.entity.FeedImageMappingEntity;

public interface FeedImageMappingPort {
  void saveAll(final List<FeedImageMappingEntity> feedImageMappingEntities);
  List<FeedImageMappingEntity> searchByFeedIds(final List<Integer> feedIds);
  void removeAllByFeedId(final List<Integer> feedIds);
  void remove(final Integer fileId);
  void removeAllByFileId(final List<Integer> fileIds);
}
