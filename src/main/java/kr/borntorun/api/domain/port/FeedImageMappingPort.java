package kr.borntorun.api.domain.port;

import java.util.List;

import kr.borntorun.api.domain.entity.FeedImageMappingEntity;

public interface FeedImageMappingPort {
  void saveAll(final List<FeedImageMappingEntity> feedImageMappingEntities);
  List<FeedImageMappingEntity> searchByFeedIds(final List<Long> feedIds);
  void removeAllByFeedId(final List<Long> feedIds);
  void remove(final long fileId);
  void removeAllByFileId(final List<Long> fileIds);
}
