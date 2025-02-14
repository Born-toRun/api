package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "댓글 수정")
public record ModifyCommentRequest(@Schema(description = "내용") @NotNull String contents) {}