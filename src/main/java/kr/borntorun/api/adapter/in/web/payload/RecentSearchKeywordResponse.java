package kr.borntorun.api.adapter.in.web.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "최근 검색어 조회")
public record RecentSearchKeywordResponse(@Schema(description = "검색어") Set<Object> searchKeywords) {
}
