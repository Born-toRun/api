package kr.borntorun.api.domain.port.model;

public record CreateCommentCommand(long myUserId,
								   Long feedId,
								   Long parentCommentId,
								   String contents) {
}
