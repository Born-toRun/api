package kr.borntorun.api.core.converter;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.adapter.in.web.payload.CreateCrewRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchCrewResponse;
import kr.borntorun.api.domain.entity.CrewEntity;
import kr.borntorun.api.domain.port.model.CreateCrewCommand;
import kr.borntorun.api.domain.port.model.Crew;
import kr.borntorun.api.infrastructure.model.CreateCrewQuery;

@Mapper
public interface CrewConverter {

  CrewConverter INSTANCE = Mappers.getMapper(CrewConverter.class);

  @Mapping(target = "imageUri", source = "imageEntity.fileUri")
  @Mapping(target = "logoUri", source = "logoEntity.fileUri")
  Crew toCrew(final CrewEntity source);

  List<Crew> toCrew(final List<CrewEntity> source);

  @Mapping(target = "crewName", source = "name")
  @Mapping(target = "crewSnsUri", source = "sns")
  SearchCrewResponse.CrewDetail toSearchCrewResponseCrewDetail(final Crew source);

  List<SearchCrewResponse.CrewDetail> toSearchCrewResponseCrewDetail(final List<Crew> source);

  CreateCrewCommand toCreateCrewCommand(CreateCrewRequest source);

  CreateCrewQuery toCreateCrewQuery(CreateCrewCommand source);

  CrewEntity toCrewEntity(CreateCrewQuery source);
}
