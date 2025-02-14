package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.RecommendationConverter;
import kr.borntorun.api.domain.port.RecommendationPort;
import kr.borntorun.api.domain.port.model.CreateRecommendationCommand;
import kr.borntorun.api.domain.port.model.RemoveRecommendationCommand;
import kr.borntorun.api.infrastructure.RecommendationGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendationService implements RecommendationPort {

  private final RecommendationGateway recommendationGateway;

  @Transactional
  @Override
  public void create(final CreateRecommendationCommand command) {
    recommendationGateway.create(RecommendationConverter.INSTANCE.toCreateRecommendationQuery(command));
  }

  @Transactional
  @Override
  public void remove(final RemoveRecommendationCommand command) {
    recommendationGateway.remove(RecommendationConverter.INSTANCE.toRemoveRecommendationQuery(command));
  }
}
