package kr.borntorun.api.domain.port.model;

public record CancelBookmarkMarathonCommand(long marathonId, long myUserId) {
}
