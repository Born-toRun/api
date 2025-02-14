package kr.borntorun.api.domain.port.model;

import java.util.List;

import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.constant.FeedCategory;

public record CreateFeedCommand(List<Integer> imageIds,
                                String contents,
                                FeedCategory category,
                                FeedAccessLevel accessLevel,
                                int myUserId) {}
