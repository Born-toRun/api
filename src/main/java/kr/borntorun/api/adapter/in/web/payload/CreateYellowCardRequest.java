package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "신고하기")
public record CreateYellowCardRequest(@NotNull @Schema(description = "신고할 유저 식별자") long targetUserId,
                                      @NotNull @Schema(description = "신고 사유: 직접입력일 경우 필수") String reason,
                                      @NotNull @Schema(description = "신고 컨텐츠 내용(댓글이면 댓글 내용, 피드면 피드 내용)") String basis) {}
