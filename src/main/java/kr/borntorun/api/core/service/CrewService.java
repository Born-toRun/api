package kr.borntorun.api.core.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.CrewConverter;
import kr.borntorun.api.domain.entity.CrewEntity;
import kr.borntorun.api.domain.port.CrewPort;
import kr.borntorun.api.domain.port.model.CreateCrewCommand;
import kr.borntorun.api.domain.port.model.Crew;
import kr.borntorun.api.infrastructure.CrewGateway;
import kr.borntorun.api.infrastructure.model.CreateCrewQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CrewService implements CrewPort {

	private final CrewConverter crewConverter;

	private final CrewGateway crewGateway;

	@Transactional(readOnly = true)
	@Override
	public List<Crew> searchAll() {
		List<CrewEntity> crewEntities = crewGateway.searchAll();
		return crewConverter.toCrew(crewEntities);
	}

	@Override
	public void create(CreateCrewCommand command) {
		CreateCrewQuery query = crewConverter.toCreateCrewQuery(command);
		crewGateway.create(query);
	}
}
