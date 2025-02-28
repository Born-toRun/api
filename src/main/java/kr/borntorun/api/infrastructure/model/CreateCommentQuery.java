package kr.borntorun.api.infrastructure.model;

public record CreateCommentQuery(long myUserId,
								 Long feedId,
								 Long parentCommentId,
								 String contents) {
}
