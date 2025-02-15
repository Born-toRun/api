package kr.borntorun.api.domain.port.model;

public record CrewDetail(String id,
                         String crewName,
                         String contents,
                         String imageUri,
                         String logoUri,
                         String crewSnsUri) {}
