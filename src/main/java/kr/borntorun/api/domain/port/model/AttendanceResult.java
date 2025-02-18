package kr.borntorun.api.domain.port.model;

import java.util.List;

public record AttendanceResult(Participant host,
                               List<Participant> participants) {
  public record Participant(int userId,
                            String userName,
                            String crewName,
                            String userProfileUri) {}
}
