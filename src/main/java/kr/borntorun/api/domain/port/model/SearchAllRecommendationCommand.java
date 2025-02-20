package kr.borntorun.api.domain.port.model;

import java.util.List;

import kr.borntorun.api.domain.constant.RecommendationType;

public record SearchAllRecommendationCommand(RecommendationType recommendationType,
                                             List<Long> contentIds) {}
