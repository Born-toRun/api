package kr.borntorun.api.domain.port.model;

public record LoginResult(String accessToken,
                          Boolean isMember) {}
