package kr.borntorun.api.domain.port.model;

public record ModifyUserCommand(int userId,
    int profileImageId,
    String instagramId
) {
}