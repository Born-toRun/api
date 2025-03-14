package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.domain.entity.RecommendationEntity;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {

	RecommendationEntity findByUserIdAndRecommendationTypeAndContentId(long userId,
	  RecommendationType recommendationType, long contentId);

	List<RecommendationEntity> findAllByUserId(long userId);

	List<RecommendationEntity> findAllByRecommendationTypeAndContentIdIn(RecommendationType recommendationType,
	  List<Long> contentIds);
}
