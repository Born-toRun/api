package kr.borntorun.api.adapter.in.web.payload;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "행사 출석 현황 조회")
public record AttendanceActivityResponse(@Schema(description = "호스트") Person host,
                                         @Schema(description = "참여 예정자") List<Person> participants) {

  public record Person(@Schema(description = "참여 예정자 식별자") long userId,
                       @Schema(description = "참여 예정자 성명") String userName,
                       @Schema(description = "참여 예정자 크루명") String crewName,
                       @Schema(description = "참여 예정자 프로필 사진 url") String userProfileUri) {}
}
