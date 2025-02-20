package kr.borntorun.api.infrastructure.model;

import java.util.List;

import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.constant.FeedCategory;

public record CreateFeedQuery(String contents,
                             List<Long> imageIds,
                             FeedCategory category,
                             FeedAccessLevel accessLevel,
							  long userId) {}
