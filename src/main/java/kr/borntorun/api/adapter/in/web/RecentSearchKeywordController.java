package kr.borntorun.api.adapter.in.web;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.borntorun.api.adapter.in.web.payload.RecentSearchKeywordResponse;
import kr.borntorun.api.adapter.in.web.proxy.RecentSearchKeywordProxy;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "최근 검색어", description = "최근 검색어 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recent-search-keywords")
public class RecentSearchKeywordController {

	private final RecentSearchKeywordProxy recentSearchKeywordProxy;

	@Operation(summary = "최근 검색어 추가", description = "최근 검색어를 추가합니다.")
	@PostMapping(value = "/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getRecentSearchKeyword(@AuthUser TokenDetail my, @PathVariable String keyword) {
		if (my.isLogin()) {
			recentSearchKeywordProxy.add(my.getId(), keyword);
		}
	}

	@Operation(summary = "최근 검색어 전체 삭제", description = "최근 검색어 전체를 삭제합니다.")
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public void removeAll(@AuthUser TokenDetail my) {
		recentSearchKeywordProxy.removeAll(my.getId());
	}

	@Operation(summary = "최근 검색어 삭제", description = "최근 검색어를 삭제합니다.")
	@DeleteMapping(value = "/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void removeKeyword(@AuthUser TokenDetail my, @PathVariable String keyword) {
		recentSearchKeywordProxy.removeKeyword(my.getId(), keyword);
	}

	@Operation(summary = "최근 검색어 조회", description = "최근 검색어를 조회합니다.")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RecentSearchKeywordResponse> getRecentSearchKeyword(@AuthUser TokenDetail my) {
		RecentSearchKeywordResponse response;
		if (my.isLogin()) {
			response = new RecentSearchKeywordResponse(Collections.emptySet());
			return ResponseEntity.ok(response);
		}

		Set<Object> recentSearchKeywords = new HashSet<>(recentSearchKeywordProxy.search(my.getId()));
		response = new RecentSearchKeywordResponse(recentSearchKeywords);
		return ResponseEntity.ok(response);
	}
}


