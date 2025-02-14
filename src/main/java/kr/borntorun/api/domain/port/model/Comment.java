package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
  private int commentId;
  private int parentId;
  private int reCommentQty;
  private int feedId;
  private String contents;
  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private Boolean isDeleted;
  private Writer writer;
  private Boolean isMyComment;
  public record Writer(
      int userId,
      String userName,
      String profileImageUri,
      String crewName,
      Boolean isAdmin,
      Boolean isManager
  ) {}
}