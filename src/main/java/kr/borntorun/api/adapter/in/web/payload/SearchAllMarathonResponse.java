package kr.borntorun.api.adapter.in.web.payload;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "마라톤 대회 조회")
public record SearchAllMarathonResponse(@Schema(description = "마라톤") List<marathon> marathons) {

	public record marathon(@Schema(description = "마라톤 식별자") long id,
						   @Schema(description = "마라톤 대화명") String title,
						   @Schema(description = "마라톤 대회 일시") String schedule,
						   @Schema(description = "마라톤 대회 장소") String venue,
						   @Schema(description = "마라톤 대회 코스") String course,
						   @Schema(description = "즐겨찾기 여부") Boolean isBookmarking) {
	}
}
