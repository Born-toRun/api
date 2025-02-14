package kr.borntorun.api.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.RecommendationRepository;
import kr.borntorun.api.domain.entity.RecommendationEntity;
import kr.borntorun.api.infrastructure.model.CreateRecommendationQuery;
import kr.borntorun.api.infrastructure.model.RemoveRecommendationQuery;
import kr.borntorun.api.infrastructure.model.SearchAllRecommendationQuery;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecommendationGateway {

  private final RecommendationRepository recommendationRepository;

  public List<RecommendationEntity> searchAll(SearchAllRecommendationQuery query) {
    return recommendationRepository.findAllByRecommendationTypeAndContentIdIn(query.recommendationType(), query.contentIds());
  }

  public void create(final CreateRecommendationQuery query) {
    recommendationRepository.save(RecommendationEntity.builder()
        .userId(query.myUserId())
        .contentId(query.contentId())
        .recommendationType(query.recommendationType())
        .build());
  }

  public void remove(final RemoveRecommendationQuery query) {
    final RecommendationEntity recommendationEntity = recommendationRepository.findByUserIdAndRecommendationTypeAndContentId(query.myUserId(), query.recommendationType(), query.contentId());
    recommendationEntity.remove();
    recommendationRepository.save(recommendationEntity);
  }

  public void removeAll(final int userId) {
    recommendationRepository.deleteAllById(recommendationRepository.findAllByUserId(userId).stream()
        .map(RecommendationEntity::getId)
        .collect(Collectors.toList()));
  }
}
