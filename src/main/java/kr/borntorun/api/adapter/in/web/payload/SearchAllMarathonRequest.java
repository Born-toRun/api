package kr.borntorun.api.adapter.in.web.payload;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@Schema(description = "마라톤 리스트 조회")
public class SearchAllMarathonRequest {
  @Schema(description = "위치") List<String> locations;
  @Schema(description = "코스") List<String> courses;
}
