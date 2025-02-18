package kr.borntorun.api.adapter.in.web;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.borntorun.api.adapter.in.web.payload.AttendanceActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.AttendanceActivityResponse;
import kr.borntorun.api.adapter.in.web.payload.CreateActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.ModifyActivityRequest;
import kr.borntorun.api.adapter.in.web.payload.OpenActivityResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchActivityDetailResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchActivityResponse;
import kr.borntorun.api.adapter.in.web.payload.SearchAllActivityRequest;
import kr.borntorun.api.adapter.in.web.proxy.ActivityProxy;
import kr.borntorun.api.core.converter.ActivityConverter;
import kr.borntorun.api.domain.port.model.Activity;
import kr.borntorun.api.domain.port.model.AttendanceResult;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "모임", description = "모임 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {

  private final ActivityConverter activityConverter;

  private final ActivityProxy activityProxy;

  @Operation(summary = "모임 생성", description = "모임 참여/불참을 받기 위해 생성합니다.")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public void create(@AuthUser TokenDetail my, @RequestBody @Valid CreateActivityRequest request) {
    activityProxy.create(my, request);
  }

  @Operation(summary = "모임 수정", description = "모임를 수정합니다.")
  @PutMapping(value = "/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void modify(@AuthUser TokenDetail my, @PathVariable int activityId, @RequestBody @Valid ModifyActivityRequest request) {
    activityProxy.modify(request, activityId);
  }

  @Operation(summary = "모임 삭제", description = "모임를 삭제합니다.")
  @DeleteMapping(value = "/{activityId}")
  public void remove(@AuthUser TokenDetail my, @PathVariable int activityId) {
    activityProxy.remove(activityId);
  }

  @Operation(summary = "모임 참여", description = "모임에 참여할 예정입니다.")
  @PostMapping(value = "/participation/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void participate(@AuthUser TokenDetail my, @PathVariable int activityId) {
    activityProxy.participate(activityId, my.getId());
  }

  @Operation(summary = "모임 불참", description = "모임에 불참할 예정입니다.")
  @PostMapping(value = "/participation-cancel/{participationId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void participateCancel(@AuthUser TokenDetail my, @PathVariable int participationId) {
    activityProxy.participateCancel(participationId);
  }

  @Operation(summary = "모임 목록 조회", description = "모임 목록을 조회합니다.")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SearchActivityResponse> searchAll(@AuthUser TokenDetail my, @ModelAttribute @Valid SearchAllActivityRequest request) {
    final List<Activity> activityEntities = activityProxy.searchAll(request, my);
    List<SearchActivityResponse.Activity> activities = activityConverter.toSearchActivityResponseActivity(activityEntities);
    SearchActivityResponse response = new SearchActivityResponse(activities);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "모임 상세 조회", description = "모임 상세를 조회합니다.")
  @GetMapping(value = "/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SearchActivityDetailResponse> search(@AuthUser TokenDetail my, @PathVariable int activityId) {
    final Activity activity = activityProxy.search(activityId, my);
    SearchActivityDetailResponse response = activityConverter.toSearchActivityDetailResponse(activity);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "모임 오픈", description = "모임를 오픈합니다.")
  @PutMapping(value = "/open/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OpenActivityResponse> open(@AuthUser TokenDetail my, @PathVariable int activityId) {
    final Activity activity = activityProxy.open(activityId);
    OpenActivityResponse response = activityConverter.toOpenActivityResponse(activity);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "출석 현황", description = "모임의 출석 현황을 조회합니다.")
  @GetMapping(value = "/attendance/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AttendanceActivityResponse> searchAttendance(@AuthUser TokenDetail my, @PathVariable int activityId) {
    final AttendanceResult attendanceResult = activityProxy.getAttendance(activityId);
    AttendanceActivityResponse response = activityConverter.toAttendanceActivityResponse(attendanceResult);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "모임 출석", description = "모임에 출석합니다.")
  @PostMapping(value = "/attendance/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void attendance(@AuthUser TokenDetail my, @PathVariable int activityId, @RequestBody @Valid AttendanceActivityRequest request) {
    activityProxy.attendance(request, activityId, my.getId());
  }
}


