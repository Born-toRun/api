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

	void create(CreateActivityCommand command);

	void modify(ModifyActivityCommand command);

	void removeAll(long userId);

	void remove(long activityId);

	void participate(ParticipateActivityCommand command);

	void participateCancel(long participationId);

	List<Activity> searchAll(SearchAllActivityCommand command);

	Activity search(long activityId, long myUserId);

	Activity open(long activityId);

	void attendance(AttendanceActivityCommand command);

	AttendanceResult getAttendance(long activityId);
}
