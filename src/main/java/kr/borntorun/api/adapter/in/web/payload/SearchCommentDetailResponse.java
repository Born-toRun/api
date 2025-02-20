package kr.borntorun.api.adapter.in.web.payload;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 상세 조회")
public record SearchCommentDetailResponse(
  @Schema(description = "댓글 번호") long id,
  @Schema(description = "작성자") Writer writer,
  @Schema(description = "내용") String contents,
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @Schema(description = "작성일자") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime registeredAt,
  @Schema(description = "대댓글 목록") List<ReComment> reComments
) {
	@Schema(description = "작성자")
	public record Writer(
	  @Schema(description = "식별자") long userId,
	  @Schema(description = "성명") String userName,
	  @Schema(description = "프로필 이미지") String profileImageUri,
	  @Schema(description = "크루명") String crewName,
	  @Schema(description = "관리자 여부") Boolean isAdmin,
	  @Schema(description = "운영진 여부") Boolean isManager
	) {
	}

	@Schema(description = "대댓글")
	public record ReComment(
	  @Schema(description = "댓글 번호") long id,
	  @Schema(description = "내용") String contents,
	  @Schema(description = "작성일자") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime registeredAt,
	  @Schema(description = "작성자") Writer writer,
	  @Schema(description = "나의 댓글 여부") Boolean isMyComment
	) {
		@Schema(description = "작성자")
		public record Writer(
		  @Schema(description = "작성자") long userId,
		  @Schema(description = "성명") String userName,
		  @Schema(description = "프로필 이미지") String profileImageUri,
		  @Schema(description = "크루명") String crewName,
		  @Schema(description = "관리자 여부") Boolean isAdmin,
		  @Schema(description = "운영진 여부") Boolean isManager
		) {
		}
	}
}