package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDetail(int id,
                            int parentId,
                            int feedId,
                            String contents,
                            LocalDateTime registeredAt,
                            LocalDateTime updatedAt,
                            Writer writer,
                            List<Comment> reComments) {

  public record Writer(int userId,
                       String userName,
                       String profileImageUri,
                       String crewName,
                       Boolean isAdmin,
                       Boolean isManager) {}
}
