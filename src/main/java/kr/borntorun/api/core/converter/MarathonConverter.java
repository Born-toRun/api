package kr.borntorun.api.core.converter;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.borntorun.api.adapter.in.web.payload.SearchAllMarathonRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchAllMarathonResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchMarathonDetailResponse;
import kr.borntorun.api.domain.entity.MarathonEntity;
import kr.borntorun.api.domain.port.model.BookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.CancelBookmarkMarathonCommand;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.domain.port.model.MarathonDetail;
import kr.borntorun.api.domain.port.model.SearchAllMarathonCommand;
import kr.borntorun.api.domain.port.model.SearchMarathonDetailCommand;
import kr.borntorun.api.infrastructure.model.BookmarkMarathonQuery;
import kr.borntorun.api.infrastructure.model.SearchMarathonQuery;

@Mapper(componentModel = "spring")
public interface MarathonConverter {

  SearchAllMarathonCommand toSearchAllMarathonCommand(final SearchAllMarathonRequest request, final long myUserId);

  SearchMarathonQuery toSearchMarathonQuery(final SearchAllMarathonCommand source);

  List<Marathon> toMarathon(final List<MarathonEntity> source, @Context final long myUserId);

  List<SearchAllMarathonResponse.marathon> toSearchMarathonResponseMarathon(final List<Marathon> source);

  SearchMarathonDetailCommand toSearchMarathonDetailCommand(final Long marathonId, final long myUserId);

  SearchMarathonDetailResponse toSearchMarathonDetailResponse(final MarathonDetail source);

  @Mapping(target = "isBookmarking", source = ".", qualifiedByName = "convertIsBookmarking")
  @Mapping(target = "id", source = "id")
  MarathonDetail toMarathonDetail(final MarathonEntity source, @Context final long myUserId);

  @Mapping(target = "isBookmarking", source = ".", qualifiedByName = "convertIsBookmarking")
  @Mapping(target = "id", source = "id")
  Marathon toMarathon(final MarathonEntity source, @Context final long myUserId);

  BookmarkMarathonCommand toBookmarkMarathonCommand(final Long marathonId, final long myUserId);

  CancelBookmarkMarathonCommand toCancelBookmarkMarathonCommand(final Long marathonId, final long myUserId);

  BookmarkMarathonQuery toBookmarkMarathonQuery(final BookmarkMarathonCommand source);

  BookmarkMarathonQuery toBookmarkMarathonQuery(final CancelBookmarkMarathonCommand source);

  @Named("convertIsBookmarking")
  default Boolean convertIsBookmarking(final MarathonEntity source, @Context final long myUserId) {
    return source.getMarathonBookmarkEntities().stream()
        .anyMatch(e -> e.getUserId() == myUserId);
  }
}
