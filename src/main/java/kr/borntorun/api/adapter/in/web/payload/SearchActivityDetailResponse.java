package kr.borntorun.api.adapter.in.web.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "크루별 행사 조회")
public record SearchActivityDetailResponse(

  @Schema(description = "행사 식별자")
  long id,

  @Schema(description = "행사 명칭")
  String title,

  @Schema(description = "내용")
  String contents,

  @Schema(description = "시작 일시")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  LocalDateTime startDate,

  @Schema(description = "집합 장소")
  String venue,

  @Schema(description = "집합 장소 지도 url")
  String venueUrl,

  @Schema(description = "인원 제한")
  int participantsLimit,

  @Schema(description = "참여 인원 수")
  int participantsQty,

  @Schema(description = "참가비")
  int participationFee,

  @Schema(description = "코스")
  String course,

  @Schema(description = "코스 설명")
  String courseDetail,

  @Schema(description = "경로")
  String path,

  @Schema(description = "행사 호스트")
  Host host,

  @Schema(description = "행사 오픈 여부")
  Boolean isOpen,

  @Schema(description = "행사 업데이트 일자")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  LocalDateTime updatedAt,

  @Schema(description = "행사 등록 일자")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  LocalDateTime registeredAt
) {

	@Schema(description = "호스트")
	public record Host(@Schema(description = "식별자") long userId,
					   @Schema(description = "크루 식별자") Long crewId,
					   @Schema(description = "프로필 이미지") String userProfileUri,
					   @Schema(description = "성명") String userName,
					   @Schema(description = "크루 명") String crewName,
					   @Schema(description = "운영진 여부") Boolean isManager,
					   @Schema(description = "런에이서 관리자 여부") Boolean isAdmin) {
	}
}
