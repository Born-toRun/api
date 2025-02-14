package kr.borntorun.api.adapter.out.persistence.querydsl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.borntorun.api.adapter.repository.entity.QActivityEntity;
import kr.borntorun.api.domain.entity.ActivityEntity;
import kr.borntorun.api.infrastructure.model.SearchAllActivityQuery;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ActivityQuery {

  private final JPAQueryFactory queryFactory;

  public List<ActivityEntity> searchAllByFilter(final SearchAllActivityQuery query) {
    final QActivityEntity activity = QActivityEntity.activityEntity;

    BooleanExpression whereClause = activity.userEntity.crewId.eq(query.myCrewId())
        .and(activity.isDeleted.eq(false));
    BooleanExpression optionalWhereClause = null;


    if (!ObjectUtils.isEmpty(query.courses())) {
      for (final String course : query.courses()) {
        if (optionalWhereClause == null) {
          optionalWhereClause = activity.course.contains(course);
        } else {
          optionalWhereClause = optionalWhereClause.or(activity.course.contains(course));
        }
      }

      whereClause = whereClause.and(optionalWhereClause);
    }

    return queryFactory
        .selectFrom(activity)
        .where(whereClause)
        .orderBy(activity.id.asc())
        .fetch();
  }
}