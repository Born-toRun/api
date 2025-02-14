package kr.borntorun.api.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.borntorun.api.adapter.in.web.payload.SearchCrewResponse;
import kr.borntorun.api.adapter.in.web.proxy.CrewProxy;
import kr.borntorun.api.core.converter.CrewConverter;
import lombok.RequiredArgsConstructor;

@Tag(name = "크루", description = "크루 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/crews")
public class CrewController {

  private final CrewProxy crewProxy;

  @Operation(summary = "크루 조회", description = "등록된 모든 크루를 조회합니다.")
  @RequestMapping(value = "", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
  public ResponseEntity<SearchCrewResponse> searchAll() {
    return ResponseEntity.ok(new SearchCrewResponse(CrewConverter.INSTANCE.toSearchCrewResponseCrewDetail(crewProxy.searchAll())));
  }
}