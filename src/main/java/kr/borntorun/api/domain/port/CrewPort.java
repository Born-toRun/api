package kr.borntorun.api.domain.port;

import java.util.List;

import kr.borntorun.api.domain.port.model.CreateCrewCommand;
import kr.borntorun.api.domain.port.model.Crew;

public interface CrewPort {

  List<Crew> searchAll();

  void create(CreateCrewCommand command);
}