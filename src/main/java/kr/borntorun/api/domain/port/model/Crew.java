package kr.borntorun.api.domain.port.model;

public record Crew(long id,
                   String name,
                   String contents,
                   String region,
                   String sns,
                   String imageUri,
                   String logoUri) {}
