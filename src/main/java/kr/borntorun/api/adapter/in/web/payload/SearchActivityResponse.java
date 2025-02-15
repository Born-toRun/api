package kr.borntorun.api.adapter.in.web.payload;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.borntorun.api.domain.constant.ActivityRecruitmentType;

@Schema(description = "크루별 모임 목록 조회")
public record SearchActivityResponse(@Schema(description = "모임 리스트") List<Activity> activities) {

  @Schema(description = "호스트")
  public record Host(@Schema(description = "식별자") int userId,
                     @Schema(description = "크루 식별자") int crewId,
                     @Schema(description = "프로필 이미지") String userProfileUri,
                     @Schema(description = "성명") String userName,
                     @Schema(description = "크루 명") String crewName,
                     @Schema(description = "운영진 여부") Boolean isManager,
                     @Schema(description = "런에이서 관리자 여부") Boolean isAdmin) {}

  @Schema(description = "모임")
  public record Activity(@Schema(description = "모임 식별자") int id,
                         @Schema(description = "모임 명칭") String title,
                         @Schema(description = "모임 호스트") Host host,
                         @Schema(description = "모임 시작 일자") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
                         @Schema(description = "모임 코스") String course,
                         @Schema(description = "모임 참여 제한 인원 수") int participantsLimit,
                         @Schema(description = "모임 참여 예정 인원 수") int participantsQty,
                         @Schema(description = "모임 업데이트 일자") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime updatedAt,
                         @Schema(description = "모임 등록 일자") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime registeredAt,
                         @Schema(description = "모임 오픈 여부") Boolean isOpen,
                         @Schema(description = "모임 모집 상태") ActivityRecruitmentType recruitmentType) {}
}
