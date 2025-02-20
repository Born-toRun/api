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

	void removeAll(final long userId);

	void remove(final long activityId);

	void participate(final ParticipateActivityCommand command);

	void participateCancel(final long participationId);

	List<Activity> searchAll(final SearchAllActivityCommand command);

	Activity search(final long activityId, final long myUserId);

	Activity open(final long activityId);

	void attendance(final AttendanceActivityCommand command);

	AttendanceResult getAttendance(final long activityId);
}
