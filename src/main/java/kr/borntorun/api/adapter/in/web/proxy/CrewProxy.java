package kr.borntorun.api.adapter.in.web.proxy;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.in.web.payload.CreateCrewRequest;
import kr.borntorun.api.core.converter.CrewConverter;
import kr.borntorun.api.core.service.CrewService;
import kr.borntorun.api.domain.port.model.CreateCrewCommand;
import kr.borntorun.api.domain.port.model.Crew;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "crew")
public class CrewProxy {

  private final CrewService crewService;

  @Cacheable(key = "'searchAll'")
  public List<Crew> searchAll() {
    return crewService.searchAll();
  }

  @CacheEvict(allEntries = true)
  public void create(CreateCrewRequest request) {
    CreateCrewCommand command = CrewConverter.INSTANCE.toCreateCrewCommand(request);
    crewService.create(command);
  }
}
