package kr.borntorun.api.domain.port.model;

public record Crew(int crewId,
                   String name,
                   String contents,
                   String region,
                   String sns,
                   String imageUri,
                   String logoUri,
                   boolean isDeleted) {}
