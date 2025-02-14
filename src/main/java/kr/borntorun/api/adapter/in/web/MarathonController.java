package kr.borntorun.api.adapter.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.borntorun.api.adapter.in.web.payload.BookmarkMarathonResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchMarathonDetailResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchMarathonRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchMarathonResponse;
import kr.borntorun.api.adapter.in.web.proxy.MarathonProxy;
import kr.borntorun.api.core.converter.MarathonConverter;
import kr.borntorun.api.domain.port.model.Marathon;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "마라톤 대회", description = "마라톤 대회 조회 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/marathons")
public class MarathonController {

  private final MarathonProxy marathonProxy;

  @Operation(summary = "마라톤 리스트 조회", description = "마라톤 대회 리스트를 조회합니다.")
  @RequestMapping(value = "", method= RequestMethod.GET)
  public ResponseEntity<SearchMarathonResponse> search(@AuthUser TokenDetail my,
      @Schema(description = "위치") @RequestParam(required = false) List<String> locations,
      @Schema(description = "코스") @RequestParam(required = false) List<String> courses) {
    List<Marathon> marathons = marathonProxy.search(new SearchMarathonRequest(locations , courses), my);
    return ResponseEntity.ok(new SearchMarathonResponse(MarathonConverter.INSTANCE.toSearchMarathonResponseMarathon(marathons)));
  }

  @Operation(summary = "마라톤 상세 조회", description = "마라톤 대회 상세를 조회합니다.")
  @RequestMapping(value = "/{marathonId}", method= RequestMethod.GET)
  public ResponseEntity<SearchMarathonDetailResponse> search(@AuthUser TokenDetail my, @PathVariable long marathonId) {
    return ResponseEntity.ok(MarathonConverter.INSTANCE.toSearchMarathonDetailResponse(marathonProxy.detail(marathonId, my)));
  }

  @Operation(summary = "마라톤 북마크", description = "마라톤 대회를 북마크 합니다.")
  @RequestMapping(value = "/bookmark/{marathonId}", method= RequestMethod.POST)
  public ResponseEntity<BookmarkMarathonResponse> bookmark(@AuthUser TokenDetail my, @PathVariable long marathonId) {
    return ResponseEntity.ok(new BookmarkMarathonResponse(marathonProxy.bookmark(marathonId, my)));
  }

  @Operation(summary = "마라톤 북마크 취소", description = "마라톤 대회 북마크를 취소합니다.")
  @RequestMapping(value = "/bookmark/{marathonId}", method= RequestMethod.DELETE)
  public ResponseEntity<BookmarkMarathonResponse> cancelBookmark(@AuthUser TokenDetail my, @PathVariable long marathonId) {
    return ResponseEntity.ok(new BookmarkMarathonResponse(marathonProxy.cancelBookmark(marathonId, my)));
  }
}
