package kr.borntorun.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  @Query(
      "SELECT u FROM UserEntity u " +
          "JOIN FETCH u.crewEntity " +
          "LEFT JOIN FETCH u.feedEntities " +
          "LEFT JOIN FETCH u.activityEntities " +
          "LEFT JOIN FETCH u.activityParticipationEntities " +
          "LEFT JOIN FETCH u.authorityEntities " +
          "LEFT JOIN FETCH u.commentEntities " +
          "LEFT JOIN FETCH u.marathonBookmarkEntities " +
          "LEFT JOIN FETCH u.objectStorageEntities " +
          "LEFT JOIN FETCH u.recommendationEntities " +
          "LEFT JOIN FETCH u.userPrivacyEntity " +
          "LEFT JOIN FETCH u.yellowCardEntities " +
          "WHERE u.id =:id"
  )
  Optional<UserEntity> findByIdAndIsDeletedFalse(long id);

  @Query(
      "SELECT u FROM UserEntity u " +
          "JOIN FETCH u.crewEntity " +
          "LEFT JOIN FETCH u.feedEntities " +
          "LEFT JOIN FETCH u.activityEntities " +
          "LEFT JOIN FETCH u.activityParticipationEntities " +
          "LEFT JOIN FETCH u.authorityEntities " +
          "LEFT JOIN FETCH u.commentEntities " +
          "LEFT JOIN FETCH u.marathonBookmarkEntities " +
          "LEFT JOIN FETCH u.objectStorageEntities " +
          "LEFT JOIN FETCH u.recommendationEntities " +
          "LEFT JOIN FETCH u.userPrivacyEntity " +
          "LEFT JOIN FETCH u.yellowCardEntities " +
          "WHERE u.name =:userName"
  )
  List<UserEntity> findAllByNameContainingAndIsDeletedFalse(String userName);
}
