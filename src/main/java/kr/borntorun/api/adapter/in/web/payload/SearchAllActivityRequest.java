package kr.borntorun.api.adapter.in.web.payload;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.borntorun.api.domain.constant.ActivityRecruitmentType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Schema(description = "행사 상세 조회")
public class SearchAllActivityRequest {
  @Schema(description = "코스")
  private List<String> courses;

  @Schema(description = "모집 상태")
  private ActivityRecruitmentType recruitmentType;
}