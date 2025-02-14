package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "object_storage")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@DynamicInsert
public class ObjectStorageEntity {

  @Id
  @Column(name = "file_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int userId;
  private String fileUri;
  private LocalDateTime uploadAt;
  private Boolean isDeleted;

  @OneToMany(mappedBy = "objectStorageEntity")
  private Set<FeedImageMappingEntity> feedImageMappingEntities;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private UserEntity userEntity;

  @OneToOne(mappedBy = "imageEntity")
  private CrewEntity crewImageEntity;

  @OneToOne(mappedBy = "logoEntity")
  private CrewEntity crewLogoEntity;

  public void remove() {
    this.isDeleted = true;
  }

  public static ObjectStorageEntity defaultEntity() {
    return ObjectStorageEntity.builder()
        .build();
  }
}
