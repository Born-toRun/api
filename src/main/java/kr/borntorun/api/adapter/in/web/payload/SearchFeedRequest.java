package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.borntorun.api.domain.constant.FeedCategory;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class SearchFeedRequest {

  @Schema(description = "피드 종류")
  private FeedCategory category;

  @Schema(description = "통합 검색어")
  private String searchKeyword;

  @Schema(description = "내 크루만 보기")
  private boolean isMyCrew;
}