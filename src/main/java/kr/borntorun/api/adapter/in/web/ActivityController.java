package kr.borntorun.api.adapter.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  private final ActivityProxy activityProxy;

  @Operation(summary = "모임 생성", description = "모임 참여/불참을 받기 위해 생성합니다.")
  @RequestMapping(value = "", method= RequestMethod.POST)
  public void create(@AuthUser TokenDetail my, @RequestBody @Valid CreateActivityRequest request) {
    activityProxy.create(my, request);
  }

  @Operation(summary = "모임 수정", description = "모임를 수정합니다.")
  @RequestMapping(value = "/{activityId}", method= RequestMethod.PUT)
  public void modify(@AuthUser TokenDetail my, @PathVariable int activityId, @RequestBody @Valid ModifyActivityRequest request) {
    activityProxy.modify(request, activityId);
  }

  @Operation(summary = "모임 삭제", description = "모임를 삭제합니다.")
  @RequestMapping(value = "/{activityId}", method= RequestMethod.DELETE)
  public void remove(@AuthUser TokenDetail my, @PathVariable int activityId) {
    activityProxy.remove(activityId);
  }

  @Operation(summary = "모임 참여", description = "모임에 참여할 예정입니다.")
  @RequestMapping(value = "/participation/{activityId}", method= RequestMethod.POST)
  public void participate(@AuthUser TokenDetail my, @PathVariable int activityId) {
    activityProxy.participate(activityId, my.getId());
  }

  @Operation(summary = "모임 불참", description = "모임에 불참할 예정입니다.")
  @RequestMapping(value = "/participation-cancel/{participationId}", method= RequestMethod.POST)
  public void participateCancel(@AuthUser TokenDetail my, @PathVariable int participationId) {
    activityProxy.participateCancel(participationId);
  }

  @Operation(summary = "모임 목록 조회", description = "모임 목록을 조회합니다.")
  @RequestMapping(value = "", method= RequestMethod.GET)
  public ResponseEntity<SearchActivityResponse> searchAll(@AuthUser TokenDetail my, @ModelAttribute @Valid SearchAllActivityRequest request) {
    final List<Activity> activityEntities = activityProxy.searchAll(request, my);

    return ResponseEntity.ok(new SearchActivityResponse(ActivityConverter.INSTANCE.toSearchActivityResponseActivity(activityEntities)));
  }

  @Operation(summary = "모임 상세 조회", description = "모임 상세를 조회합니다.")
  @RequestMapping(value = "/{activityId}", method= RequestMethod.GET)
  public ResponseEntity<SearchActivityDetailResponse> search(@AuthUser TokenDetail my, @PathVariable int activityId) {
    final Activity activity = activityProxy.search(activityId, my);

    return ResponseEntity.ok(ActivityConverter.INSTANCE.toSearchActivityDetailResponse(activity));
  }

  @Operation(summary = "모임 오픈", description = "모임를 오픈합니다.")
  @RequestMapping(value = "/open/{activityId}", method= RequestMethod.PUT)
  public ResponseEntity<OpenActivityResponse> open(@AuthUser TokenDetail my, @PathVariable int activityId) {
    final Activity activity = activityProxy.open(activityId);

    return ResponseEntity.ok(ActivityConverter.INSTANCE.toOpenActivityResponse(activity));
  }

  @Operation(summary = "출석 현황", description = "모임의 출석 현황을 조회합니다.")
  @RequestMapping(value = "/attendance/{activityId}", method= RequestMethod.GET)
  public ResponseEntity<AttendanceActivityResponse> searchAttendance(@AuthUser TokenDetail my, @PathVariable int activityId) {
    final AttendanceResult attendanceResult = activityProxy.getAttendance(activityId);

    return ResponseEntity.ok(ActivityConverter.INSTANCE.toAttendanceActivityResponse(attendanceResult));
  }

  @Operation(summary = "모임 출석", description = "모임에 출석합니다.")
  @RequestMapping(value = "/attendance/{activityId}", method= RequestMethod.POST)
  public void attendance(@AuthUser TokenDetail my, @PathVariable int activityId, @RequestBody @Valid AttendanceActivityRequest request) {
    activityProxy.attendance(request, activityId, my.getId());
  }
}


