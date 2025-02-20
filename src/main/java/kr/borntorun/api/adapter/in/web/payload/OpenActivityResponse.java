package kr.borntorun.api.adapter.in.web.payload;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "크루별 행사 오픈")
public record OpenActivityResponse(@Schema(description = "행사 식별자") long activityId,
                                   @Schema(description = "행사 참여 코드") int attendanceCode) {}
