package kr.borntorun.api.domain.port.model;

public record CreateCommentCommand(long myUserId,
								   long feedId,
								   long parentCommentId,
                                   String contents) {}
