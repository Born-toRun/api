package kr.borntorun.api.domain.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ActivityRecruitmentType {

	RECRUITING("모집중"),
	CLOSED("정원마감"),
	ALREADY_PARTICIPATING("참여완료"),
	ENDED("종료");

	private final String desc;
}
