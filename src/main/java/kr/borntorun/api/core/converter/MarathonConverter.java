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

	SearchAllMarathonCommand toSearchAllMarathonCommand(SearchAllMarathonRequest request, long myUserId);

	SearchMarathonQuery toSearchMarathonQuery(SearchAllMarathonCommand source);

	List<Marathon> toMarathon(List<MarathonEntity> source, @Context long myUserId);

	List<SearchAllMarathonResponse.marathon> toSearchMarathonResponseMarathon(List<Marathon> source);

	SearchMarathonDetailCommand toSearchMarathonDetailCommand(Long marathonId, long myUserId);

	SearchMarathonDetailResponse toSearchMarathonDetailResponse(MarathonDetail source);

	@Mapping(target = "isBookmarking", source = ".", qualifiedByName = "convertIsBookmarking")
	@Mapping(target = "id", source = "id")
	MarathonDetail toMarathonDetail(MarathonEntity source, @Context long myUserId);

	@Mapping(target = "isBookmarking", source = ".", qualifiedByName = "convertIsBookmarking")
	@Mapping(target = "id", source = "id")
	Marathon toMarathon(MarathonEntity source, @Context long myUserId);

	BookmarkMarathonCommand toBookmarkMarathonCommand(Long marathonId, long myUserId);

	CancelBookmarkMarathonCommand toCancelBookmarkMarathonCommand(Long marathonId, long myUserId);

	BookmarkMarathonQuery toBookmarkMarathonQuery(BookmarkMarathonCommand source);

	BookmarkMarathonQuery toBookmarkMarathonQuery(CancelBookmarkMarathonCommand source);

	@Named("convertIsBookmarking")
	default Boolean convertIsBookmarking(MarathonEntity source, @Context long myUserId) {
		return source.getMarathonBookmarkEntities().stream()
		  .anyMatch(e -> e.getUserId() == myUserId);
	}
}
