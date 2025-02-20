package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.CreateRecommendationCommand;
import kr.borntorun.api.domain.port.model.RemoveRecommendationCommand;

public interface RecommendationPort {

	void create(final CreateRecommendationCommand command);

	void remove(final RemoveRecommendationCommand command);
}
