package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.CrewRepository;
import kr.borntorun.api.domain.entity.CrewEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CrewGateway {

  private final CrewRepository crewRepository;

  public List<CrewEntity> searchAll() {
    return crewRepository.findAll();
  }
}
