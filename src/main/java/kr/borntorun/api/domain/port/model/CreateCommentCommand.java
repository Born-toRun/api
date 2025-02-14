package kr.borntorun.api.domain.port.model;

public record CreateCommentCommand(int myUserId,
                                   int feedId,
                                   Integer parentCommentId,
                                   String contents) {}
