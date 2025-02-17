package kr.borntorun.api.adapter.in.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.CreateCrewRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchCrewResponse;
import kr.borntorun.api.adapter.in.web.proxy.CrewProxy;
import kr.borntorun.api.core.converter.CrewConverter;
import kr.borntorun.api.domain.port.model.Crew;
import lombok.RequiredArgsConstructor;

@Tag(name = "크루", description = "크루 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/crews")
public class CrewController {

  private final CrewProxy crewProxy;

  @Operation(summary = "크루 조회", description = "등록된 모든 크루를 조회합니다.")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SearchCrewResponse> searchAll() {
    List<Crew> crews = crewProxy.searchAll();
    List<SearchCrewResponse.CrewDetail> crewDetails = CrewConverter.INSTANCE.toSearchCrewResponseCrewDetail(crews);
    return ResponseEntity.ok(new SearchCrewResponse(crewDetails));
  }

  @Operation(summary = "크루 등록", description = "크루를 등록합니다.")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public void create(@RequestBody @Valid CreateCrewRequest request) {
    crewProxy.create(request);
  }
}