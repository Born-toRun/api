package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.borntorun.api.domain.constant.RecommendationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class CommentEntity {

  @Id
  @Column(name = "comment_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int parentId;
  private int userId;
  private int feedId;
  private String contents;
  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feedId", insertable = false, updatable = false)
  private FeedEntity feedEntity;

  @OneToMany(mappedBy = "commentContentsEntity")
  private Set<RecommendationEntity> recommendationEntities;

  public void remove() {
    this.isDeleted = true;
  }

  public long getRecommendationQty() {
    return recommendationEntities.stream()
        .filter(e -> e.getRecommendationType().equals(RecommendationType.FEED))
        .filter(e -> !e.getIsDeleted())
        .count();
  }
}


