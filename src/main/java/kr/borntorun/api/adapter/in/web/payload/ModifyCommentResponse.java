package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 수정")
public record ModifyCommentResponse(@Schema(description = "댓글 번호") long id,
									@Schema(description = "내용") String contents) {
}
