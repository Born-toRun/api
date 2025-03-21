package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.RecommendationConverter;
import kr.borntorun.api.domain.port.RecommendationPort;
import kr.borntorun.api.domain.port.model.CreateRecommendationCommand;
import kr.borntorun.api.domain.port.model.RemoveRecommendationCommand;
import kr.borntorun.api.infrastructure.RecommendationGateway;
import kr.borntorun.api.infrastructure.model.CreateRecommendationQuery;
import kr.borntorun.api.infrastructure.model.RemoveRecommendationQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendationService implements RecommendationPort {

	private final RecommendationConverter recommendationConverter;

	private final RecommendationGateway recommendationGateway;

	@Transactional
	@Override
	public void create(CreateRecommendationCommand command) {
		CreateRecommendationQuery query = recommendationConverter.toCreateRecommendationQuery(command);
		recommendationGateway.create(query);
	}

	@Transactional
	@Override
	public void remove(RemoveRecommendationCommand command) {
		RemoveRecommendationQuery query = recommendationConverter.toRemoveRecommendationQuery(command);
		recommendationGateway.remove(query);
	}
}
