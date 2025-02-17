package kr.borntorun.api.domain.port.model;

public record CreateCrewCommand(String name,
								String contents,
								String sns,
								String region) {}
