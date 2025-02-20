package kr.borntorun.api.adapter.out.persistence.querydsl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.entity.FeedEntity;
import kr.borntorun.api.domain.entity.QFeedEntity;
import kr.borntorun.api.infrastructure.model.SearchAllFeedQuery;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedQuery {

  private final JPAQueryFactory queryFactory;

  public Page<FeedEntity> searchAllByFilter(final SearchAllFeedQuery query, final Pageable pageable) {
    final QFeedEntity feed = QFeedEntity.feedEntity;

    BooleanExpression whereClause = Expressions.TRUE; // 기본값 설정

    if (Objects.nonNull(query.category())) {
      whereClause = whereClause.and(feed.category.eq(query.category()));
    }

    if (query.lastFeedId() > 0) {
      whereClause = whereClause.and(feed.id.lt(query.lastFeedId()));
    }

    // 내 크루만 공개
    if (query.isMyCrew()) {
      whereClause = whereClause.and(
        feed.userEntity.crewId.eq(query.my().getCrewId())
          .and(feed.accessLevel.eq(FeedAccessLevel.IN_CREW))
      );
    } else {
      whereClause = whereClause.and(feed.accessLevel.eq(FeedAccessLevel.ALL));
    }

    // 내용 내 검색어
    if (query.isUsedIntegratedSearch()) {
      BooleanExpression searchWhereClause = feed.contents.matches(query.searchKeyword());

      if (!query.searchedUserIds().isEmpty()) {
        searchWhereClause = searchWhereClause.or(feed.userId.in(query.searchedUserIds()));
      }

      whereClause = whereClause.and(searchWhereClause);
    }

    final int total = queryFactory
      .selectFrom(feed)
      .where(whereClause)
      .fetch().size();

    final List<FeedEntity> contents = queryFactory
      .selectFrom(feed)
      .where(whereClause)
      .orderBy(feed.id.desc())
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    return new PageImpl<>(contents, pageable, total);
  }

  public void increaseViewQty(final long feedId) {
    final QFeedEntity feed = QFeedEntity.feedEntity;

    queryFactory.update(feed)
        .set(feed.viewQty, feed.viewQty.add(1))
        .where(feed.id.eq(feedId))
        .execute();
  }
}