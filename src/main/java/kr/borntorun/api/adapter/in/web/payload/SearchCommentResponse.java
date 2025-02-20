package kr.borntorun.api.adapter.in.web.payload;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "댓글 목록 조회")
@Builder
public record SearchCommentResponse(@Schema(description = "댓글") List<Comment> comments) {

  @Schema(description = "댓글")
  public record Comment(@Schema(description = "댓글 번호") long id,
                        @Schema(description = "부모 댓글 번호") long parentId,
                        @Schema(description = "대댓글 개수") int reCommentQty,
                        @Schema(description = "작성자") Writer writer,
                        @Schema(description = "내용") String contents,
                        @Schema(description = "작성일자") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime registeredAt,
                        @Schema(description = "내가 쓴 댓글 여부") Boolean isMyComment) {

    @Schema(description = "대댓글 여부")
    public boolean getIsReComment() {
      return parentId > 0;
    }
  }

  @Schema(description = "작성자")
  public record Writer(@Schema(description = "작성자 식별자") long userId,
                       @Schema(description = "성명") String userName,
                       @Schema(description = "프로필 이미지") String profileImageUri,
                       @Schema(description = "크루명") String crewName,
                       @Schema(description = "관리자 여부") Boolean isAdmin,
                       @Schema(description = "운영진 여부") Boolean isManager) {}
}
