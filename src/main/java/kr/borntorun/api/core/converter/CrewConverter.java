package kr.borntorun.api.core.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.borntorun.api.adapter.in.web.payload.CreateCrewRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchCrewResponse;
import kr.borntorun.api.domain.entity.CrewEntity;
import kr.borntorun.api.domain.port.model.CreateCrewCommand;
import kr.borntorun.api.domain.port.model.Crew;
import kr.borntorun.api.infrastructure.model.CreateCrewQuery;

@Mapper(componentModel = "spring")
public interface CrewConverter {

	@Mapping(target = "imageUri", source = "imageEntity.fileUri")
	@Mapping(target = "logoUri", source = "logoEntity.fileUri")
	Crew toCrew(CrewEntity source);

	List<Crew> toCrew(List<CrewEntity> source);

	@Mapping(target = "crewName", source = "name")
	@Mapping(target = "crewSnsUri", source = "sns")
	SearchCrewResponse.CrewDetail toSearchCrewResponseCrewDetail(Crew source);

	List<SearchCrewResponse.CrewDetail> toSearchCrewResponseCrewDetail(List<Crew> source);

	CreateCrewCommand toCreateCrewCommand(CreateCrewRequest source);

	CreateCrewQuery toCreateCrewQuery(CreateCrewCommand source);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "imageId", ignore = true)
	@Mapping(target = "logoId", ignore = true)
	@Mapping(target = "userEntities", ignore = true)
	@Mapping(target = "imageEntity", ignore = true)
	@Mapping(target = "logoEntity", ignore = true)
	CrewEntity toCrewEntity(CreateCrewQuery source);
}
