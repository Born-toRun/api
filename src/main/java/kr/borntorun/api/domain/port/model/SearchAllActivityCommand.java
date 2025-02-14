package kr.borntorun.api.domain.port.model;

import java.util.List;

import kr.borntorun.api.domain.constant.ActivityRecruitmentType;

public record SearchAllActivityCommand(List<String> courses, ActivityRecruitmentType recruitmentType, int myCrewId, int myUserId) {}
