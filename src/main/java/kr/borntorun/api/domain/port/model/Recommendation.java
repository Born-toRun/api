package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

import kr.borntorun.api.domain.constant.RecommendationType;

public record Recommendation(int recommendationId,
                             int contentId,
                             int userId,
                             RecommendationType recommendationType,
                             LocalDateTime registeredAt) {}
