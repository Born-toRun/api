package kr.borntorun.api.infrastructure.model;

public record CreateCommentQuery(int myUserId,
                                 int feedId,
                                 Integer parentCommentId,
                                 String contents) {}
