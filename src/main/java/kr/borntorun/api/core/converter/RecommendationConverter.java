package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;

import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.domain.port.model.CreateRecommendationCommand;
import kr.borntorun.api.domain.port.model.RemoveRecommendationCommand;
import kr.borntorun.api.infrastructure.model.CreateRecommendationQuery;
import kr.borntorun.api.infrastructure.model.RemoveRecommendationQuery;

@Mapper(componentModel = "spring")
public interface RecommendationConverter {

	CreateRecommendationCommand toCreateRecommendationCommand(long myUserId,
	  RecommendationType recommendationType, long contentId);

	RemoveRecommendationCommand toRemoveRecommendationCommand(long myUserId,
	  RecommendationType recommendationType, long contentId);

	CreateRecommendationQuery toCreateRecommendationQuery(CreateRecommendationCommand source);

	RemoveRecommendationQuery toRemoveRecommendationQuery(RemoveRecommendationCommand source);
}
