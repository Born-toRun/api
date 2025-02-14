package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "댓글 작성")
public record CreateCommentRequest(@Schema(description = "부모 댓글 식별자 (대댓글인 경우 필수)") Integer parentCommentId,
                                   @Schema(description = "내용") @NotNull String contents) {}