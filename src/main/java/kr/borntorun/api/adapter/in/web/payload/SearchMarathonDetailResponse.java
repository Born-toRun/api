package kr.borntorun.api.adapter.in.web.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "마라톤 대회 상세 조회")
public record SearchMarathonDetailResponse(@Schema(description = "마라톤 식별자") long marathonId,
                                           @Schema(description = "대화명") String title,
                                           @Schema(description = "대표자명") String owner,
                                           @Schema(description = "대표 이메일") String email,
                                           @Schema(description = "일시") String schedule,
                                           @Schema(description = "전화번호") String contact,
                                           @Schema(description = "코스") String course,
                                           @Schema(description = "지역") String location,
                                           @Schema(description = "장소") String venue,
                                           @Schema(description = "주최단체") String host,
                                           @Schema(description = "접수기간") String duration,
                                           @Schema(description = "홈페이지") String homepage,
                                           @Schema(description = "대회장") String venueDetail,
                                           @Schema(description = "비고") String remark,
                                           @Schema(description = "등록일시")
                                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime registeredAt,
                                           @Schema(description = "나의 북마크 여부") Boolean isBookmarking) {}
