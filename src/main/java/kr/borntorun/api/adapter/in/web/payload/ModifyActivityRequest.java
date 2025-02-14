package kr.borntorun.api.adapter.in.web.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "행사 수정")
public record ModifyActivityRequest(
    @Schema(description = "제목")
    @NotNull
    String title,

    @Schema(description = "내용")
    @NotNull
    String contents,

    @Schema(description = "시작 일시")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @NotNull
    LocalDateTime startDate,

    @Schema(description = "집합 장소")
    String venue,

    @Schema(description = "집합 장소 지도 url")
    @NotNull
    String venueUrl,

    @Schema(description = "인원 제한")
    @NotNull
    int participantsLimit,

    @Schema(description = "참가비")
    int participationFee,

    @Schema(description = "코스")
    String course,

    @Schema(description = "코스 설명")
    String courseDetail,

    @Schema(description = "경로")
    String path
) {
}
