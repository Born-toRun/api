package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
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
  @Column(name = "feed_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int userId;
  private String contents;
  @Enumerated(EnumType.STRING)
  private FeedCategory category;
  @Enumerated(EnumType.STRING)
  private FeedAccessLevel accessLevel;
  private int viewQty;
  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private UserEntity userEntity;

  @OneToMany(mappedBy = "feedEntity")
  private Set<CommentEntity> commentEntities;

  @OneToMany(mappedBy = "feedEntity")
  private Set<FeedImageMappingEntity> feedImageMappingEntities;

  @OneToMany(mappedBy = "feedContentsEntity")
  private Set<RecommendationEntity> recommendationEntities;

  public void add(final List<FeedImageMappingEntity> feedImageMappingEntities) {
    for(final FeedImageMappingEntity entity: feedImageMappingEntities) {
      entity.setFeedEntity(this);
    }

    this.feedImageMappingEntities.addAll(feedImageMappingEntities);
  }

  public void remove() {
    this.isDeleted = true;
  }

  public void modify(final ModifyFeedQuery query) {
    this.accessLevel = query.accessLevel();
    this.contents = query.contents();
    this.category = query.category();
  }

  public long getRecommendationQty() {
    return recommendationEntities.stream()
        .filter(e -> e.getRecommendationType().equals(RecommendationType.FEED))
        .filter(e -> !e.getIsDeleted())
        .count();
  }

  public long getCommentQty() {
    return commentEntities.stream()
        .filter(e -> !e.getIsDeleted())
        .count();
  }

  public List<String> getImageUris() {
    return feedImageMappingEntities.stream()
        .filter(e -> !e.getIsDeleted())
        .map(FeedImageMappingEntity::getObjectStorageEntity)
        .filter(e -> !e.getIsDeleted())
        .map(ObjectStorageEntity::getFileUri)
        .toList();
  }

  public boolean hasComment(final int myUserId) {
    if(myUserId == -1) {
      return false;
    }

    return commentEntities.stream()
        .filter(e -> !e.getIsDeleted())
        .anyMatch(e -> e.getUserId() == myUserId);
  }

  public boolean hasRecommendation(final int myUserId) {
    if(myUserId == -1) {
      return false;
    }

    return recommendationEntities.stream()
        .filter(e -> !e.getIsDeleted())
        .anyMatch(e -> e.getUserId() == myUserId);
  }
}