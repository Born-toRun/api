package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.domain.entity.RecommendationEntity;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {

  RecommendationEntity findByUserIdAndRecommendationTypeAndContentId(final long userId, final RecommendationType recommendationType, final long contentId);
  List<RecommendationEntity> findAllByUserId(final long userId);
  List<RecommendationEntity> findAllByRecommendationTypeAndContentIdIn(final RecommendationType recommendationType, final List<Long> contentIds);
}
