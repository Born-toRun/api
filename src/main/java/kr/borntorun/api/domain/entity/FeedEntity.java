package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.borntorun.api.domain.constant.FeedAccessLevel;
import kr.borntorun.api.domain.constant.FeedCategory;
import kr.borntorun.api.domain.constant.RecommendationType;
import kr.borntorun.api.infrastructure.model.ModifyFeedQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "feed")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@DynamicInsert
public class FeedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long userId;
  private String contents;
  @Enumerated(EnumType.STRING)
  private FeedCategory category;
  @Enumerated(EnumType.STRING)
  private FeedAccessLevel accessLevel;
  private int viewQty;
  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private UserEntity userEntity;

  @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE)
  private Set<CommentEntity> commentEntities;

  @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE)
  private Set<FeedImageMappingEntity> feedImageMappingEntities;

  @OneToMany(mappedBy = "feedContentsEntity", cascade = CascadeType.REMOVE)
  private Set<RecommendationEntity> recommendationEntities;

  public void add(final List<FeedImageMappingEntity> feedImageMappingEntities) {
    for(final FeedImageMappingEntity entity: feedImageMappingEntities) {
      entity.setFeedEntity(this);
    }

    this.feedImageMappingEntities.addAll(feedImageMappingEntities);
  }

  public void modify(final ModifyFeedQuery query) {
    this.accessLevel = query.accessLevel();
    this.contents = query.contents();
    this.category = query.category();
  }

  public long getRecommendationQty() {
    return recommendationEntities.stream()
        .filter(e -> e.getRecommendationType().equals(RecommendationType.FEED))
        .count();
  }

  public long getCommentQty() {
    return commentEntities.size();
  }

  public List<String> getImageUris() {
    return feedImageMappingEntities.stream()
        .map(FeedImageMappingEntity::getObjectStorageEntity)
        .map(ObjectStorageEntity::getFileUri)
        .toList();
  }

  public boolean hasComment(final long myUserId) {
    if(myUserId == -1) {
      return false;
    }

    return commentEntities.stream()
        .anyMatch(e -> e.getUserId() == myUserId);
  }

  public boolean hasRecommendation(final long myUserId) {
    if(myUserId == -1) {
      return false;
    }

    return recommendationEntities.stream()
        .anyMatch(e -> e.getUserId() == myUserId);
  }
}