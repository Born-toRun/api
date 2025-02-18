package kr.borntorun.api.adapter.in.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.CreateFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.DetailFeedResponse;
import kr.borntorun.api.adapter.in.web.payload.ModifyFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchFeedResponse;
import kr.borntorun.api.adapter.in.web.proxy.FeedProxy;
import kr.borntorun.api.core.converter.FeedConverter;
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

  private final FeedConverter feedConverter;

  private final FeedProxy feedProxy;

  @Operation(summary = "피드 상세 보기", description = "특정 피드를 조회합니다.")
  @GetMapping(value = "{feedId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DetailFeedResponse> detail(@AuthUser TokenDetail my, @PathVariable final int feedId) {
    final Feed feed = feedProxy.searchDetail(my, feedId);
    feedProxy.increaseViewQty(feedId);

    return ResponseEntity.ok(feedConverter.toDetailFeedResponse(feed));
  }

  @Operation(summary = "피드 작성", description = "피드를 작성합니다.")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public void create(@AuthUser TokenDetail my, @RequestBody @Valid final CreateFeedRequest request) {
    feedProxy.create(request, my);
  }

  @Operation(summary = "피드 삭제", description = "피드를 삭제합니다.")
  @DeleteMapping(value = "/{feedId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void remove(@AuthUser TokenDetail my, @PathVariable final int feedId) {
    feedProxy.remove(feedId, my);
  }

  @Operation(summary = "피드 수정", description = "피드를 수정합니다.")
  @PutMapping(value = "/{feedId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void modify(@AuthUser TokenDetail my, @PathVariable final int feedId, @RequestBody @Valid final ModifyFeedRequest request) {
    feedProxy.modify(request, feedId);
  }

  @Operation(summary = "피드 목록 조회", description = "피드 목록을 조회합니다.")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<SearchFeedResponse>> searchAll(@AuthUser TokenDetail my,
      @Valid @ModelAttribute SearchFeedRequest request,
      @RequestParam(defaultValue = "0") int lastFeedId,
      @RequestParam(defaultValue = "10") int size) {
    final Page<FeedCard> feedPage = feedProxy.searchAll(request, my, lastFeedId, PageRequest.of(0, size));

    return ResponseEntity.ok(feedPage.map(feedConverter::toSearchFeedResponse));
  }
}


