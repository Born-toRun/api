package kr.borntorun.api.adapter.in.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.CreateFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.DetailFeedResponse;
import kr.borntorun.api.adapter.in.web.payload.ModifyFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchFeedResponse;
import kr.borntorun.api.adapter.in.web.proxy.FeedProxy;
import kr.borntorun.api.core.converter.FeedConverter;
import kr.borntorun.api.domain.constant.FeedCategory;
import kr.borntorun.api.domain.port.model.Feed;
import kr.borntorun.api.domain.port.model.FeedCard;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "피드", description = "피드 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/feeds")
public class FeedController {

  private final FeedProxy feedProxy;

  @Operation(summary = "피드 상세 보기", description = "특정 피드를 조회합니다.")
  @RequestMapping(value = "{feedId}", method= RequestMethod.GET)
  public ResponseEntity<DetailFeedResponse> detail(@AuthUser TokenDetail my, @PathVariable final int feedId) {
    final Feed feed = feedProxy.searchDetail(my, feedId);
    feedProxy.increaseViewQty(feedId);

    return ResponseEntity.ok(FeedConverter.INSTANCE.toDetailFeedResponse(feed));
  }

  @Operation(summary = "피드 작성", description = "피드를 작성합니다.")
  @RequestMapping(value = "", method= RequestMethod.POST)
  public void create(@AuthUser TokenDetail my, @RequestBody @Valid final CreateFeedRequest request) {
    feedProxy.create(request, my);
  }

  @Operation(summary = "피드 삭제", description = "피드를 삭제합니다.")
  @RequestMapping(value = "/{feedId}", method= RequestMethod.DELETE)
  public void remove(@AuthUser TokenDetail my, @PathVariable final int feedId) {
    feedProxy.remove(feedId, my);
  }

  @Operation(summary = "피드 수정", description = "피드를 수정합니다.")
  @RequestMapping(value = "/{feedId}", method= RequestMethod.PUT)
  public void modify(@AuthUser TokenDetail my, @PathVariable final int feedId, @RequestBody @Valid final ModifyFeedRequest request) {
    feedProxy.modify(request, feedId);
  }

  @Operation(summary = "피드 목록 조회", description = "피드 목록을 조회합니다.")
  @RequestMapping(value = "", method= RequestMethod.GET)
  public ResponseEntity<Page<SearchFeedResponse>> searchAll(@AuthUser TokenDetail my,
      @Schema(description = "피드 종류") @RequestParam(required = false) FeedCategory category,
      @Schema(description = "통합 검색어") @RequestParam(required = false) String searchKeyword,
      @Schema(description = "내 크루만 보기") @RequestParam(required = false, defaultValue = "false") Boolean isMyCrew,
      @RequestParam(defaultValue = "0") int lastFeedId,
      @RequestParam(defaultValue = "10") int size) {
    final Page<FeedCard> feedPage = feedProxy.searchAll(new SearchFeedRequest(category, searchKeyword, isMyCrew), my, lastFeedId, PageRequest.of(0, size));

    return ResponseEntity.ok(feedPage.map(FeedConverter.INSTANCE::toSearchFeedResponse));
  }
}


