package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import kr.borntorun.api.core.converter.RecommendationConverter;
import kr.borntorun.api.core.service.RecommendationService;
import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.domain.port.model.CreateRecommendationCommand;
import kr.borntorun.api.domain.port.model.RemoveRecommendationCommand;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "recommendation")
public class RecommendationProxy {

	private final RecommendationConverter recommendationConverter;

	private final RecommendationService recommendationService;

	@CacheEvict(allEntries = true, cacheNames = {"recommendation", "feed"})
	public void create(TokenDetail my, RecommendationType recommendationType, long contentId) {
		CreateRecommendationCommand command = recommendationConverter.toCreateRecommendationCommand(my.getId(),
		  recommendationType, contentId);
		recommendationService.create(command);
	}

	@CacheEvict(allEntries = true, cacheNames = {"recommendation", "feed"})
	public void remove(TokenDetail my, RecommendationType recommendationType, long contentId) {
		RemoveRecommendationCommand command = recommendationConverter.toRemoveRecommendationCommand(my.getId(),
		  recommendationType, contentId);
		recommendationService.remove(command);
	}
}
