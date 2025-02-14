package kr.borntorun.api.domain.port.model;

import kr.borntorun.api.domain.constant.RecommendationType;

public record CreateRecommendationCommand(RecommendationType recommendationType,
                                          int contentId,
                                          int myUserId) {}
