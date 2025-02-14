package kr.borntorun.api.core.converter;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.adapter.in.web.payload.SearchMarathonDetailResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchMarathonRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchMarathonResponse;
import kr.borntorun.api.domain.entity.MarathonEntity;
import kr.borntorun.api.domain.port.model.BookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.CancelBookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.domain.port.model.MarathonDetail;
import kr.borntorun.api.domain.port.model.SearchMarathonCommand;
import kr.borntorun.api.domain.port.model.SearchMarathonDetailCommand;
import kr.borntorun.api.infrastructure.model.BookmarkMarathonQuery;
import kr.borntorun.api.infrastructure.model.SearchMarathonQuery;

@Mapper
public interface MarathonConverter {

  MarathonConverter INSTANCE = Mappers.getMapper(MarathonConverter.class);

  SearchMarathonCommand toSearchMarathonCommand(final SearchMarathonRequest request, final int myUserId);

  SearchMarathonQuery toSearchMarathonQuery(final SearchMarathonCommand source);

  @Mapping(target = "isBookmarking", source = ".", qualifiedByName = "convertIsBookmarking")
  @Mapping(target = "marathonId", source = "id")
  Marathon toMarathon(final MarathonEntity source, @Context final int myUserId);

  List<Marathon> toMarathon(final List<MarathonEntity> source, @Context final int myUserId);

  List<SearchMarathonResponse.marathon> toSearchMarathonResponseMarathon(final List<Marathon> source);

  SearchMarathonResponse.marathon toSearchMarathonResponseMarathon(final Marathon source);

  SearchMarathonDetailCommand toSearchMarathonDetailCommand(final Long marathonId, final Integer myUserId);

  SearchMarathonDetailResponse toSearchMarathonDetailResponse(final MarathonDetail source);

  @Mapping(target = "isBookmarking", source = ".", qualifiedByName = "convertIsBookmarking")
  @Mapping(target = "marathonId", source = "id")
  MarathonDetail toMarathonDetail(final MarathonEntity source, @Context final int myUserId);

  BookmarkMarathonCommand toBookmarkMarathonCommand(final Long marathonId, final Integer myUserId);

  CancelBookmarkMarathonCommand toCancelBookmarkMarathonCommand(final Long marathonId, final Integer myUserId);

  BookmarkMarathonQuery toBookmarkMarathonQuery(final BookmarkMarathonCommand source);

  BookmarkMarathonQuery toBookmarkMarathonQuery(final CancelBookmarkMarathonCommand source);

  @Named("convertIsBookmarking")
  default Boolean convertIsBookmarking(final MarathonEntity source, @Context final int myUserId) {
    return source.getMarathonBookmarkEntities().stream()
        .anyMatch(e -> e.getUserId() == myUserId && !e.getIsDeleted());
  }
}
