package kr.borntorun.api.adapter.in.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.borntorun.api.adapter.in.web.proxy.RecommendationProxy;
import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "좋아요", description = "좋아요 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

  private final RecommendationProxy recommendationProxy;

  @Operation(summary = "좋아요 추가", description = "컨텐츠를 '좋아요'합니다.")
  @PostMapping(value = "/{recommendationType}/{contentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void recommend(@AuthUser TokenDetail my, @PathVariable final RecommendationType recommendationType, @PathVariable final long contentId) {
    recommendationProxy.create(my, recommendationType, contentId);
  }

  @Operation(summary = "좋아요 취소", description = "컨텐츠의 '좋아요'를 취소 합니다.")
  @DeleteMapping(value = "/{recommendationType}/{contentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void remove(@AuthUser TokenDetail my, @PathVariable final RecommendationType recommendationType, @PathVariable final long contentId) {
    recommendationProxy.remove(my, recommendationType, contentId);
  }
}
