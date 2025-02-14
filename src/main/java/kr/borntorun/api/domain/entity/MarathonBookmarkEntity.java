package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "marathon_bookmark")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class MarathonBookmarkEntity {

  @Id
  @Column(name = "bookmark_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int userId;
  private long marathonId;
  private LocalDateTime registeredAt;
  private LocalDateTime updatedAt;
  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "marathonId", insertable = false, updatable = false)
  private MarathonEntity marathonEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private UserEntity userEntity;
}


