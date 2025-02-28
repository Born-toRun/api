package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDetail(long id,
							Long parentId,
							Long feedId,
							String contents,
							LocalDateTime registeredAt,
							LocalDateTime updatedAt,
							Writer writer,
							List<CommentResult> reCommentResults) {

	public record Writer(long userId,
						 String userName,
						 String profileImageUri,
						 String crewName,
						 Boolean isAdmin,
						 Boolean isManager) {
	}
}
