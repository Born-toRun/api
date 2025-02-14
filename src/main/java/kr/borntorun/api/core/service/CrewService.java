package kr.borntorun.api.core.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.CrewConverter;
import kr.borntorun.api.domain.port.CrewPort;
import kr.borntorun.api.domain.port.model.Crew;
import kr.borntorun.api.infrastructure.CrewGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CrewService implements CrewPort {

  private final CrewGateway crewGateway;

  @Transactional(readOnly = true)
  @Override
  public List<Crew> searchAll() {
    return CrewConverter.INSTANCE.toCrew(crewGateway.searchAll());
  }
}
