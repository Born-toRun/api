package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.domain.entity.RecommendationEntity;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Integer> {

  RecommendationEntity findByUserIdAndRecommendationTypeAndContentId(final int userId, final RecommendationType recommendationType, final int contentId);
  List<RecommendationEntity> findAllByUserId(final int userId);
  List<RecommendationEntity> findAllByRecommendationTypeAndContentIdIn(final RecommendationType recommendationType, final List<Integer> contentIds);
  List<RecommendationEntity> findByRecommendationTypeAndContentId(final RecommendationType recommendationType, final Integer contentId);
}
