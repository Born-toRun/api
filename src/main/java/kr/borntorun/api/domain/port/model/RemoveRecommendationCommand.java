package kr.borntorun.api.domain.port.model;

import kr.borntorun.api.domain.constant.RecommendationType;

public record RemoveRecommendationCommand(RecommendationType recommendationType,
										  long contentId,
										  long myUserId) {}
