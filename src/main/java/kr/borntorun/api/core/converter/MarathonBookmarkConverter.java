package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.domain.entity.MarathonBookmarkEntity;
import kr.borntorun.api.infrastructure.model.BookmarkMarathonQuery;

@Mapper
public interface MarathonBookmarkConverter {

  MarathonBookmarkConverter INSTANCE = Mappers.getMapper(MarathonBookmarkConverter.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "registeredAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "marathonEntity", ignore = true)
  @Mapping(target = "userEntity", ignore = true)
  @Mapping(target = "userId", source = "query.myUserId")
  MarathonBookmarkEntity toMarathonBookmarkEntity(final BookmarkMarathonQuery query, final boolean isDeleted);
}
