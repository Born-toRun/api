package kr.borntorun.api.domain.port.model;

import java.util.List;

public record SearchMarathonCommand(List<String> locations, List<String> courses, int myUserId) {}
