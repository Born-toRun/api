package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

public record CommentResult(long id,
							Long parentId,
							int reCommentQty,
							long feedId,
							String contents,
							LocalDateTime registeredAt,
							LocalDateTime updatedAt,
							Writer writer,
							Boolean isMyComment) {

	public record Writer(
	  long userId,
	  String userName,
	  String profileImageUri,
	  String crewName,
	  Boolean isAdmin,
	  Boolean isManager
	) {
	}
}