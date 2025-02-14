package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import kr.borntorun.api.core.converter.RecommendationConverter;
import kr.borntorun.api.core.service.RecommendationService;
import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "recommendation")
public class RecommendationProxy {

  private final RecommendationService recommendationService;

  @CacheEvict(allEntries = true, cacheNames = {"recommendation", "feed"})
  public void create(final TokenDetail my, final RecommendationType recommendationType, final int contentId) {
    recommendationService.create(RecommendationConverter.INSTANCE.toCreateRecommendationCommand(my.getId(), recommendationType, contentId));
  }

  @CacheEvict(allEntries = true, cacheNames = {"recommendation", "feed"})
  public void remove(final TokenDetail my, final RecommendationType recommendationType, final int contentId) {
    recommendationService.remove(RecommendationConverter.INSTANCE.toRemoveRecommendationCommand(my.getId(), recommendationType, contentId));
  }
}
