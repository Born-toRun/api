package kr.borntorun.api.domain.port.model;

public record ModifyCommentCommand(int commentId, String contents) {}
