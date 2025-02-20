package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;
import java.util.List;

import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.constant.FeedCategory;


public record Feed(
  long id,
    String contents,
    List<Image> images,
    FeedCategory category,
    FeedAccessLevel accessLevel,
    int viewQty,
    LocalDateTime registeredAt,
    LocalDateTime updatedAt,
    Writer writer,
    int recommendationQty,
    Boolean hasMyRecommendation,
    int commentQty,
    Boolean hasMyComment
) {
  public record Image(
      long id,
      String imageUri
  ) {}

  public record Writer(
    long userId,
      String userName,
      String crewName,
      String profileImageUri,
      Boolean isAdmin,
      Boolean isManager
  ) {}
}