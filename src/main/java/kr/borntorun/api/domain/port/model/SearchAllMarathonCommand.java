package kr.borntorun.api.domain.port.model;

import java.util.List;

public record SearchAllMarathonCommand(List<String> locations, List<String> courses, long myUserId) {
}
