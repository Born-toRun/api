package kr.borntorun.api.infrastructure.model;

import java.util.List;

import kr.borntorun.api.domain.constant.ActivityRecruitmentType;

public record SearchAllActivityQuery(List<String> courses, ActivityRecruitmentType recruitmentType, Long myCrewId,
									 long myUserId) {
}
