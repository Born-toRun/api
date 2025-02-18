package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.CrewRepository;
import kr.borntorun.api.core.converter.CrewConverter;
import kr.borntorun.api.domain.entity.CrewEntity;
import kr.borntorun.api.infrastructure.model.CreateCrewQuery;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CrewGateway {

  private final CrewConverter crewConverter;

  private final CrewRepository crewRepository;

  public List<CrewEntity> searchAll() {
    return crewRepository.findAll();
  }

  public void create(CreateCrewQuery query) {
    CrewEntity crewEntity = crewConverter.toCrewEntity(query);
    crewRepository.save(crewEntity);
  }
}
