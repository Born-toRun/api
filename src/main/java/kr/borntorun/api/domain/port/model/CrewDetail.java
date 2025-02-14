package kr.borntorun.api.domain.port.model;

public record CrewDetail(String crewId,
                         String crewName,
                         String contents,
                         String imageUri,
                         String logoUri,
                         String crewSnsUri) {}
