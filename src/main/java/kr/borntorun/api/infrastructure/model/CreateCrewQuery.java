package kr.borntorun.api.infrastructure.model;

public record CreateCrewQuery(String name,
							  String contents,
							  String sns,
							  String region) {
}
