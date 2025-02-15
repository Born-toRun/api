package kr.borntorun.api.infrastructure;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.YellowCardRepository;
import kr.borntorun.api.core.converter.YellowCardConverter;
import kr.borntorun.api.domain.entity.YellowCardEntity;
import kr.borntorun.api.infrastructure.model.CreateYellowCardQuery;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class YellowCardGateway {

  private final YellowCardRepository yellowCardRepository;

  public boolean exists(final int sourceUserId, final int targetUserId) {
    return yellowCardRepository.existsBySourceUserIdAndTargetUserId(sourceUserId, targetUserId);
  }

  public void create(CreateYellowCardQuery query) {
    YellowCardEntity yellowCardEntity = YellowCardConverter.INSTANCE.toYellowCardEntity(query);
    yellowCardRepository.save(yellowCardEntity);
  }
}
