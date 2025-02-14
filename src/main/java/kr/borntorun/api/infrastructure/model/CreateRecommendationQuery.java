package kr.borntorun.api.infrastructure.model;

import kr.borntorun.api.domain.constant.RecommendationType;

public record CreateRecommendationQuery(RecommendationType recommendationType,
                                        int contentId,
                                        int myUserId) {}
