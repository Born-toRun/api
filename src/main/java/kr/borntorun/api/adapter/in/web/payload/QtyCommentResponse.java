package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "댓글 개수")
@Builder
public record QtyCommentResponse(@Schema(description = "댓글 개수") int qty) {
}
