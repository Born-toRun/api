package kr.borntorun.api.infrastructure.model;

import java.util.List;

import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.constant.FeedCategory;

public record ModifyFeedQuery(int feedId,
                              List<Integer> imageIds,
                              String contents,
                              FeedCategory category,
                              FeedAccessLevel accessLevel) {}
