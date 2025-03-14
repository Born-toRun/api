package kr.borntorun.api.domain.port;

import kr.borntorun.api.domain.port.model.CreateRecommendationCommand;
import kr.borntorun.api.domain.port.model.RemoveRecommendationCommand;

public interface RecommendationPort {

	void create(CreateRecommendationCommand command);

	void remove(RemoveRecommendationCommand command);
}
