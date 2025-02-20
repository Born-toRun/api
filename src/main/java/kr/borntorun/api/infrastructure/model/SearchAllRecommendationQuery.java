package kr.borntorun.api.infrastructure.model;

import java.util.List;

import kr.borntorun.api.domain.constant.RecommendationType;

public record SearchAllRecommendationQuery(RecommendationType recommendationType,
										   List<Long> contentIds) {
}
