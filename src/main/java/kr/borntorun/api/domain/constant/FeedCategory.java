package kr.borntorun.api.domain.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FeedCategory {

  COMMUNITY("커뮤니티"),
  MARKET("마켓");

  private final String descryption;
}
