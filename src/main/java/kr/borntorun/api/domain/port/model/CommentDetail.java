package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDetail(int commentId,
                            int parentId,
                            int feedId,
                            String contents,
                            LocalDateTime registeredAt,
                            LocalDateTime updatedAt,
                            Boolean isDeleted,
                            Writer writer,
                            List<Comment> reComments) {

  public record Writer(int userId,
                       String userName,
                       String profileImageUri,
                       String crewName,
                       Boolean isAdmin,
                       Boolean isManager) {}
}
