package kr.borntorun.api.adapter.in.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	private final CommentConverter commentConverter;

	private final CommentProxy commentProxy;

	@Operation(summary = "댓글 목록 조회", description = "모든 댓글을 조회합니다.")
	@GetMapping(value = "/{feedId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchCommentResponse> searchAll(@AuthUser TokenDetail my, @PathVariable long feedId) {
		List<Comment> comments = commentProxy.searchAll(feedId, my);
		List<SearchCommentResponse.Comment> searchCommentResponseComments = commentConverter.toSearchCommentResponseComment(
		  comments);
		SearchCommentResponse response = new SearchCommentResponse(searchCommentResponseComments);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "댓글 상세 조회", description = "댓글의 상세페이지로 진입합니다.")
	@GetMapping(value = "/detail/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SearchCommentDetailResponse> detail(@AuthUser TokenDetail my, @PathVariable long commentId) {
		final CommentDetail commentDetail = commentProxy.detail(commentId, my);
		SearchCommentDetailResponse response = commentConverter.toSearchCommentDetailResponse(commentDetail,
		  my.getId());
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
	@PostMapping(value = "/{feedId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void create(@AuthUser TokenDetail my, @PathVariable long feedId,
	  @RequestBody @Valid CreateCommentRequest request) {
		commentProxy.create(my, feedId, request);
	}

	@Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
	@DeleteMapping(value = "/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void remove(@AuthUser TokenDetail my, @PathVariable long commentId) {
		commentProxy.remove(commentId);
	}

	@Operation(summary = "댓글 수정", description = "댓글 내용을 수정합니다.")
	@PutMapping(value = "/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ModifyCommentResponse> modify(@AuthUser TokenDetail my, @PathVariable long commentId,
	  @RequestBody @Valid ModifyCommentRequest request) {
		final Comment comment = commentProxy.modify(commentId, request);
		ModifyCommentResponse response = commentConverter.toModifyCommentResponse(comment);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "댓글 개수 조회", description = "댓글의 총 개수를 조회합니다.")
	@GetMapping(value = "/qty/{feedId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<QtyCommentResponse> qty(@PathVariable long feedId) {
		int qty = commentProxy.qty(feedId);
		return ResponseEntity.ok(new QtyCommentResponse(qty));
	}
}