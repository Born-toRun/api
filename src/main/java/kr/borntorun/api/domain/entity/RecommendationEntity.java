package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;

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
import jakarta.persistence.Table;
import kr.borntorun.api.domain.constant.RecommendationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "recommendation")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@DynamicInsert
public class RecommendationEntity {

  @Id
  @Column(name = "recommendation_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int contentId;
  private int userId;
  @Enumerated(EnumType.STRING)
  private RecommendationType recommendationType;
  private LocalDateTime registeredAt;
  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contentId", insertable = false, updatable = false)
  private FeedEntity feedContentsEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contentId", insertable = false, updatable = false)
  private CommentEntity commentContentsEntity;

  public void remove() {
    isDeleted = true;
  }
}
