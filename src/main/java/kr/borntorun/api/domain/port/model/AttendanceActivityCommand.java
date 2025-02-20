package kr.borntorun.api.domain.port.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttendanceActivityCommand {
	private long activityId;
	private int accessCode;
	private long myUserId;
}
