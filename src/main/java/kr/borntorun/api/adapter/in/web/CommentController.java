package kr.borntorun.api.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.CreateCommentRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyCommentRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyCommentResponse;
import kr.borntorun.api.adapter.in.web.payload.QtyCommentResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchCommentDetailResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchCommentResponse;
import kr.borntorun.api.adapter.in.web.proxy.CommentProxy;
import kr.borntorun.api.core.converter.CommentConverter;
import kr.borntorun.api.domain.port.model.Comment;
import kr.borntorun.api.domain.port.model.CommentDetail;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "댓글", description = "댓글 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

  private final CommentProxy commentProxy;

  @Operation(summary = "댓글 목록 조회", description = "모든 댓글을 조회합니다.")
  @RequestMapping(value = "/{feedId}", method= RequestMethod.GET)
  public ResponseEntity<SearchCommentResponse> searchAll(@AuthUser TokenDetail my, @PathVariable int feedId) {
    return ResponseEntity.ok(SearchCommentResponse.builder()
        .comments(CommentConverter.INSTANCE.toSearchCommentResponseComment(commentProxy.searchAll(feedId, my)))
        .build());
  }

  @Operation(summary = "댓글 상세 조회", description = "댓글의 상세페이지로 진입합니다.")
  @RequestMapping(value = "/detail/{commentId}", method= RequestMethod.GET)
  public ResponseEntity<SearchCommentDetailResponse> detail(@AuthUser TokenDetail my, @PathVariable int commentId) {
    final CommentDetail commentDetail = commentProxy.detail(commentId, my);

    return ResponseEntity.ok(CommentConverter.INSTANCE.toSearchCommentDetailResponse(commentDetail, my.getId()));
  }

  @Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
  @RequestMapping(value = "/{feedId}", method= RequestMethod.POST)
  public void create(@AuthUser TokenDetail my, @PathVariable int feedId, @RequestBody @Valid CreateCommentRequest request) {
    commentProxy.create(my, feedId, request);
  }

  @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
  @RequestMapping(value = "/{commentId}", method= RequestMethod.DELETE)
  public void remove(@AuthUser TokenDetail my, @PathVariable int commentId) {
    commentProxy.remove(commentId);
  }

  @Operation(summary = "댓글 수정", description = "댓글 내용을 수정합니다.")
  @RequestMapping(value = "/{commentId}", method= RequestMethod.PUT)
  public ResponseEntity<ModifyCommentResponse> modify(@AuthUser TokenDetail my, @PathVariable int commentId, @RequestBody @Valid ModifyCommentRequest request) {
    final Comment comment = commentProxy.modify(commentId, request);
    return ResponseEntity.ok(CommentConverter.INSTANCE.toModifyCommentResponse(comment));
  }

  @Operation(summary = "댓글 개수 조회", description = "댓글의 총 개수를 조회합니다.")
  @RequestMapping(value = "/qty/{feedId}", method= RequestMethod.GET)
  public ResponseEntity<QtyCommentResponse> qty(@PathVariable int feedId) {
    return ResponseEntity.ok(QtyCommentResponse.builder()
        .qty(commentProxy.qty(feedId))
        .build());
  }
}