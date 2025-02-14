package kr.borntorun.api.domain.port.model;

import java.util.List;

public record AttendanceResult(Person host,
                               List<Person> participants) {
  public record Person(int userId,
                       String userName,
                       String crewName,
                       String userProfileUri) {}
}
