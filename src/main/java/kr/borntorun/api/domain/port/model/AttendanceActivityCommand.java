package kr.borntorun.api.domain.port.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttendanceActivityCommand {
  private int activityId;
  private int accessCode;
  private int myUserId;
}
