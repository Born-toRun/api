package kr.borntorun.api.infrastructure.model;

import kr.borntorun.api.domain.constant.RecommendationType;

public record SearchRecommendationQuery(RecommendationType recommendationType,
										long contentId) {
}
