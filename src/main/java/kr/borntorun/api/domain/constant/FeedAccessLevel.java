package kr.borntorun.api.domain.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FeedAccessLevel {

  ALL("모두 공개"),
  IN_CREW("크루 내 공개");

  private final String description;
}
