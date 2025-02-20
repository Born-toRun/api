package kr.borntorun.api.infrastructure.model;

import java.util.List;

public record SearchMarathonQuery(List<String> locations, List<String> courses) {
}
