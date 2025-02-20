package kr.borntorun.api.domain.port.model;

import kr.borntorun.api.support.TokenDetail;

public record SearchFeedDetailCommand(TokenDetail my, long feedId) {}
