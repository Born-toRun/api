package kr.borntorun.api.domain.port;

import java.util.List;

import kr.borntorun.api.domain.port.model.Activity;
import kr.borntorun.api.domain.port.model.AttendanceActivityCommand;
import kr.borntorun.api.domain.port.model.AttendanceResult;
import kr.borntorun.api.domain.port.model.CreateActivityCommand;
import kr.borntorun.api.domain.port.model.ModifyActivityCommand;
import kr.borntorun.api.domain.port.model.ParticipateActivityCommand;
import kr.borntorun.api.domain.port.model.SearchAllActivityCommand;

public interface ActivityPort {

  void create(final CreateActivityCommand command);
  void modify(final ModifyActivityCommand command);
  void removeAll(final int userId);
  void remove(final int activityId);
  void participate(final ParticipateActivityCommand command);
  void participateCancel(final int participationId);
  List<Activity> searchAll(final SearchAllActivityCommand command);
  Activity search(final int activityId, final int myUserId);
  Activity open(final int activityId);
  void attendance(final AttendanceActivityCommand command);
  AttendanceResult getAttendance(final int activityId);
}
