package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

import kr.borntorun.api.domain.constant.RecommendationType;

public record Recommendation(long id,
							 long contentId,
							 long userId,
							 RecommendationType recommendationType,
							 LocalDateTime registeredAt) {
}
