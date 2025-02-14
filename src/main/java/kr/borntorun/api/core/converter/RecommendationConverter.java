package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.domain.port.model.CreateRecommendationCommand;
import kr.borntorun.api.domain.port.model.RemoveRecommendationCommand;
import kr.borntorun.api.infrastructure.model.CreateRecommendationQuery;
import kr.borntorun.api.infrastructure.model.RemoveRecommendationQuery;

@Mapper
public interface RecommendationConverter {

  RecommendationConverter INSTANCE = Mappers.getMapper(RecommendationConverter.class);

  CreateRecommendationCommand toCreateRecommendationCommand(final Integer myUserId, final RecommendationType recommendationType, final Integer contentId);

  RemoveRecommendationCommand toRemoveRecommendationCommand(final Integer myUserId, final RecommendationType recommendationType, final Integer contentId);

  CreateRecommendationQuery toCreateRecommendationQuery(final CreateRecommendationCommand source);

  RemoveRecommendationQuery toRemoveRecommendationQuery(final RemoveRecommendationCommand source);
}
