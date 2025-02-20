package kr.borntorun.api.domain.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecommendationType {

	FEED("피드"),
	COMMENT("댓글");

	private final String description;
}
