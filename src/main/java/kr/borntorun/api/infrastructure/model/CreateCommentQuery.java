package kr.borntorun.api.infrastructure.model;

public record CreateCommentQuery(long myUserId,
								 long feedId,
								 long parentCommentId,
								 String contents) {
}
