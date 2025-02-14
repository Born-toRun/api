package kr.borntorun.api.core.converter;

import java.util.List;
import java.util.Set;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import kr.borntorun.api.adapter.in.web.payload.CreateFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.DetailFeedResponse;
import kr.borntorun.api.adapter.in.web.payload.ModifyFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchFeedRequest;
import kr.borntorun.api.adapter.in.web.payload.SearchFeedResponse;
import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.entity.FeedEntity;
import kr.borntorun.api.domain.entity.FeedImageMappingEntity;
import kr.borntorun.api.domain.port.model.CreateFeedCommand;
import kr.borntorun.api.domain.port.model.Feed;
import kr.borntorun.api.domain.port.model.FeedCard;
import kr.borntorun.api.domain.port.model.ModifyFeedCommand;
import kr.borntorun.api.domain.port.model.RemoveFeedCommand;
import kr.borntorun.api.domain.port.model.SearchAllFeedCommand;
import kr.borntorun.api.domain.port.model.SearchFeedDetailCommand;
import kr.borntorun.api.infrastructure.model.CreateFeedQuery;
import kr.borntorun.api.infrastructure.model.ModifyFeedQuery;
import kr.borntorun.api.infrastructure.model.SearchAllFeedQuery;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.exception.InvalidException;

@Mapper
public interface FeedConverter {

  FeedConverter INSTANCE = Mappers.getMapper(FeedConverter.class);

  SearchFeedDetailCommand toSearchFeedDetailCommand(final Integer feedId, final TokenDetail my);

  @Mapping(target = "feedId", source = "source.id")
  @Mapping(target = "hasMyRecommendation", source = ".", qualifiedByName = "convertHasMyRecommendation")
  @Mapping(target = "hasMyComment", source = ".", qualifiedByName = "convertHasMyComment")
  @Mapping(target = "commentQty", source = "source.commentQty")
  @Mapping(target = "recommendationQty", source = "source.recommendationQty")
  @Mapping(target = "images", source = "source.feedImageMappingEntities", qualifiedByName = "convertImages")
  @Mapping(target = "writer", expression = "java(new Feed.Writer(source.getUserId(), source.getUserEntity().getName(), source.getUserEntity().getCrewEntity().getName(), source.getUserEntity().getProfileImageUri(), source.getUserEntity().getIsAdmin(), source.getUserEntity().getIsManager()))")
  Feed toFeed(final FeedEntity source, @Context final TokenDetail my);

  @Mapping(target = "images", source = "images", qualifiedByName = "convertDetailResponseImages")
  @Mapping(target = "writer", source = "writer", qualifiedByName = "convertDetailResponseWriter")
  @Mapping(target = "viewer.hasMyRecommendation", source = "hasMyRecommendation")
  @Mapping(target = "viewer.hasMyComment", source = "hasMyComment")
  DetailFeedResponse toDetailFeedResponse(final Feed source);

  ModifyFeedCommand toModifyFeedCommand(final ModifyFeedRequest source, final int feedId);

  ModifyFeedQuery toModifyFeedQuery(final ModifyFeedCommand source);

  RemoveFeedCommand toRemoveFeedCommand(final Integer feedId, final TokenDetail my);

  @Mapping(target = "myUserId", source = "my.id")
  CreateFeedCommand toCreateFeedCommand(final CreateFeedRequest source, final TokenDetail my);

  @Mapping(target = "userId", source = "myUserId")
  CreateFeedQuery toCreateFeedQuery(final CreateFeedCommand source);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "viewQty", ignore = true)
  @Mapping(target = "registeredAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "isDeleted", ignore = true)
  @Mapping(target = "commentEntities", ignore = true)
  @Mapping(target = "feedImageMappingEntities", ignore = true)
  @Mapping(target = "userEntity", ignore = true)
  @Mapping(target = "recommendationEntities", ignore = true)
  @Mapping(target = "imageUris", ignore = true)
  FeedEntity toFeedEntity(final CreateFeedQuery source);

  SearchAllFeedCommand toSearchAllFeedCommand(final SearchFeedRequest request, final TokenDetail my, final int lastFeedId);

  SearchAllFeedQuery toSearchAllFeedQuery(final SearchAllFeedCommand source, final List<Integer> searchedUserIds);

  @Mapping(target = "writer.userName", source = "writer.userName")
  @Mapping(target = "writer.crewName", source = "writer.crewName")
  @Mapping(target = "writer.profileImageUri", source = "writer.profileImageUri")
  @Mapping(target = "writer.isAdmin", source = "writer.isAdmin")
  @Mapping(target = "writer.isManager", source = "writer.isManager")
  @Mapping(target = "viewer.hasMyRecommendation", source = "hasRecommendation")
  @Mapping(target = "viewer.hasMyComment", source = "hasComment")
  SearchFeedResponse toSearchFeedResponse(final FeedCard source);

  @Mapping(target = "feedId", source = "source.id")
  @Mapping(target = "imageUris", source = "source.imageUris")
  @Mapping(target = "viewQty", source = "source.viewQty")
  @Mapping(target = "contents", source = "source.contents")
  @Mapping(target = "registeredAt", source = "source.registeredAt")
  @Mapping(target = "commentQty", source = "source.commentQty")
  @Mapping(target = "recommendationQty", source = "source.recommendationQty")
  @Mapping(target = "writer.userName", source = "source.userEntity.name")
  @Mapping(target = "writer.crewName", source = "source.userEntity.crewEntity.name")
  @Mapping(target = "writer.profileImageUri", source = "source.userEntity.profileImageUri")
  @Mapping(target = "writer.isAdmin", source = "source.userEntity.isAdmin")
  @Mapping(target = "writer.isManager", source = "source.userEntity.isManager")
  FeedCard toFeedCard(final FeedEntity source, final boolean hasComment, final boolean hasRecommendation);

  @Named("convertHasMyRecommendation")
  default Boolean convertHasMyRecommendation(final FeedEntity source, @Context final TokenDetail my) {
    if(my != null) {
      if(FeedAccessLevel.IN_CREW.equals(source.getAccessLevel()) && my.getCrewId() != source.getUserEntity().getCrewId()) {
        throw new InvalidException("타 크루원이 열람할 수 없는 피드입니다.");
      }

      return source.getRecommendationEntities().stream()
          .anyMatch(recommendationEntity -> recommendationEntity.getUserId() == my.getId());
    } else {
      if(!FeedAccessLevel.ALL.equals(source.getAccessLevel())) {
        throw new InvalidException("로그인이 안된 상태 혹은 타 크루원은 열람할 수 없는 피드입니다.");
      }

      return false;
    }
  }

  @Named("convertHasMyComment")
  default Boolean convertHasMyComment(final FeedEntity source, @Context final TokenDetail my) {
    if(my != null) {
      if(FeedAccessLevel.IN_CREW.equals(source.getAccessLevel()) && my.getCrewId() != source.getUserEntity().getCrewId()) {
        throw new InvalidException("타 크루원이 열람할 수 없는 피드입니다.");
      }

      return source.getCommentEntities().stream()
          .anyMatch(commentEntity -> commentEntity.getUserId() == my.getId());
    } else {
      if(!FeedAccessLevel.ALL.equals(source.getAccessLevel())) {
        throw new InvalidException("로그인이 안된 상태 혹은 타 크루원은 열람할 수 없는 피드입니다.");
      }

      return false;
    }
  }

  @Named("convertImages")
  default List<Feed.Image> convertImages(final Set<FeedImageMappingEntity> source) {
    return source.stream()
        .filter(e -> !e.getIsDeleted())
        .map(FeedImageMappingEntity::getObjectStorageEntity)
        .filter(e -> !e.getIsDeleted())
        .map(e -> new Feed.Image(e.getId(), e.getFileUri()))
        .toList();
  }

  @Named("convertDetailResponseImages")
  default List<DetailFeedResponse.Image> convertDetailResponseImages(final List<Feed.Image> source) {
    return source.stream()
        .map(e -> new DetailFeedResponse.Image(e.imageId(), e.imageUri()))
        .toList();
  }

  @Named("convertDetailResponseWriter")
  default DetailFeedResponse.Writer convertDetailResponseWriter(final Feed.Writer source) {
    return new DetailFeedResponse.Writer(source.userId(),
        source.userName(),
        source.crewName(),
        source.profileImageUri(),
        source.isAdmin(),
        source.isManager());
  }
}
