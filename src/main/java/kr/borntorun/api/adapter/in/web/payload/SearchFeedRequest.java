package kr.borntorun.api.adapter.in.web.payload;

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
  private FeedCategory category;
  private String searchKeyword;
  private Boolean isMyCrew;
}