package kr.borntorun.api.adapter.in.web.payload;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "크루 조회")
public record SearchCrewResponse(@Schema(description = "크루") List<CrewDetail> crewDetails) {

  public record CrewDetail(@Schema(description = "크루 식별자") String id,
                           @Schema(description = "크루 명") String crewName,
                           @Schema(description = "크루 소개") String contents,
                           @Schema(description = "크루 활동 지역") String region,
                           @Schema(description = "크루 대표 이미지") String imageUri,
                           @Schema(description = "크루 로고") String logoUri,
                           @Schema(description = "크루 sns") String crewSnsUri) {}
}
