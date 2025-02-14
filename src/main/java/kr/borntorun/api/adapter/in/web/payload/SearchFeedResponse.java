package kr.borntorun.api.adapter.in.web.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "피드 목록 조회")
public record SearchFeedResponse(@Schema(description = "글 번호") int feedId,
                                 @Schema(description = "이미지 uri 리스트") List<String> imageUris,
                                 @Schema(description = "본문") String contents,
                                 @Schema(description = "조회수") long viewQty,
                                 @Schema(description = "좋아요 수") long recommendationQty,
                                 @Schema(description = "댓글 수") int commentQty,
                                 @Schema(description = "작성일자") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime registeredAt,
                                 @Schema(description = "작성자") Writer writer,
                                 @Schema(description = "조회자") Viewer viewer) {

  @Schema(description = "작성자")
  public record Writer(@Schema(description = "작성자 명") String userName,
                       @Schema(description = "작성자의 크루 명") String crewName,
                       @Schema(description = "작성자 프로필 이미지") String profileImageUri,
                       @Schema(description = "작성자 관리자 여부") Boolean isAdmin,
                       @Schema(description = "작성자 운영진 여부") Boolean isManager) {}

  @Schema(description = "조회자")
  public record Viewer(@Schema(description = "나의 좋아요 여부") Boolean hasMyRecommendation,
                       @Schema(description = "나의 댓글 작성 여부") Boolean hasMyComment) {}
}
