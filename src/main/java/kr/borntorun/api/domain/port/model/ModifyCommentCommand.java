package kr.borntorun.api.domain.port.model;

public record ModifyCommentCommand(long commentId, String contents) {
}
