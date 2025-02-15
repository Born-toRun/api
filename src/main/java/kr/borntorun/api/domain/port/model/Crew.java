package kr.borntorun.api.domain.port.model;

public record Crew(int id,
                   String name,
                   String contents,
                   String region,
                   String sns,
                   String imageUri,
                   String logoUri) {}
