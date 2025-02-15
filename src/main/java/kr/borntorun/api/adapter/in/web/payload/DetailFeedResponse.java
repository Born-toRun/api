package kr.borntorun.api.adapter.in.web.payload;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.constant.FeedCategory;

@Schema(description = "피드 상세 조회")
public record DetailFeedResponse(@Schema(description = "글 번호") int id,
                                 @Schema(description = "내용") String contents,
                                 @Schema(description = "이미지") List<Image> images,
                                 @Schema(description = "카테고리") FeedCategory category,
                                 @Schema(description = "공개 구분") FeedAccessLevel accessLevel,
                                 @Schema(description = "조회수") int viewQty,
                                 @Schema(description = "좋아요 수") int recommendationQty,
                                 @Schema(description = "댓글 수") int commentQty,
                                 @Schema(description = "작성일자") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime registeredAt,
                                 @Schema(description = "작성자") Writer writer,
                                 @Schema(description = "조회자") Viewer viewer) {

  @Schema(description = "이미지")
  public record Image(@Schema(description = "이미지 식별자") int imageId,
                      @Schema(description = "이미지 uri") String imageUri) {}


  @Schema(description = "작성자")
  public record Writer(@Schema(description = "유저 식별자") int userId,
                       @Schema(description = "작성자 명") String userName,
                       @Schema(description = "작성자의 크루 명") String crewName,
                       @Schema(description = "작성자 프로필 사진") String profileImageUri,
                       @Schema(description = "작성자 관리자 여부") Boolean isAdmin,
                       @Schema(description = "작성자 운영진 여부") Boolean isManager) {}

  @Schema(description = "조회자")
  public record Viewer(@Schema(description = "나의 좋아요 여부") Boolean hasMyRecommendation,
                       @Schema(description = "나의 댓글 여부") Boolean hasMyComment) {}
}
