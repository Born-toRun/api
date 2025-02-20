package kr.borntorun.api.adapter.in.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.BookmarkMarathonResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchAllMarathonRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchAllMarathonResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchMarathonDetailResponse;
import kr.borntorun.api.adapter.in.web.proxy.MarathonProxy;
import kr.borntorun.api.core.converter.MarathonConverter;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.domain.port.model.MarathonDetail;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "마라톤 대회", description = "마라톤 대회 조회 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/marathons")
public class MarathonController {

	private final MarathonConverter marathonConverter;

	private final MarathonProxy marathonProxy;

	@Operation(summary = "마라톤 리스트 조회", description = "마라톤 대회 리스트를 조회합니다.")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchAllMarathonResponse> searchAll(@AuthUser TokenDetail my,
	  @Valid @ModelAttribute SearchAllMarathonRequest request) {
		List<Marathon> marathons = marathonProxy.search(request, my);
		List<SearchAllMarathonResponse.marathon> searchAllMarathonResponseMarathon = marathonConverter.toSearchMarathonResponseMarathon(
		  marathons);
		SearchAllMarathonResponse response = new SearchAllMarathonResponse(searchAllMarathonResponseMarathon);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "마라톤 상세 조회", description = "마라톤 대회 상세를 조회합니다.")
	@GetMapping(value = "/{marathonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchMarathonDetailResponse> search(@AuthUser TokenDetail my,
	  @PathVariable long marathonId) {
		MarathonDetail marathonDetail = marathonProxy.detail(marathonId, my);
		SearchMarathonDetailResponse response = marathonConverter.toSearchMarathonDetailResponse(marathonDetail);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "마라톤 북마크", description = "마라톤 대회를 북마크 합니다.")
	@PostMapping(value = "/bookmark/{marathonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookmarkMarathonResponse> bookmark(@AuthUser TokenDetail my, @PathVariable long marathonId) {
		long bookmarkedMarathonId = marathonProxy.bookmark(marathonId, my);
		BookmarkMarathonResponse response = new BookmarkMarathonResponse(bookmarkedMarathonId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "마라톤 북마크 취소", description = "마라톤 대회 북마크를 취소합니다.")
	@DeleteMapping(value = "/bookmark/{marathonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookmarkMarathonResponse> cancelBookmark(@AuthUser TokenDetail my,
	  @PathVariable long marathonId) {
		long bookmarkCanceledMarathonId = marathonProxy.cancelBookmark(marathonId, my);
		BookmarkMarathonResponse response = new BookmarkMarathonResponse(bookmarkCanceledMarathonId);
		return ResponseEntity.ok(response);
	}
}
