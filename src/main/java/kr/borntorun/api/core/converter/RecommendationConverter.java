package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;

import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.domain.port.model.CreateRecommendationCommand;
import kr.borntorun.api.domain.port.model.RemoveRecommendationCommand;
import kr.borntorun.api.infrastructure.model.CreateRecommendationQuery;
import kr.borntorun.api.infrastructure.model.RemoveRecommendationQuery;

@Mapper(componentModel = "spring")
public interface RecommendationConverter {

  CreateRecommendationCommand toCreateRecommendationCommand(final long myUserId, final RecommendationType recommendationType, final long contentId);

  RemoveRecommendationCommand toRemoveRecommendationCommand(final long myUserId, final RecommendationType recommendationType, final long contentId);

  CreateRecommendationQuery toCreateRecommendationQuery(final CreateRecommendationCommand source);

  RemoveRecommendationQuery toRemoveRecommendationQuery(final RemoveRecommendationCommand source);
}
