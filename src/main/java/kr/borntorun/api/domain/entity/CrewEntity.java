package kr.borntorun.api.domain.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "crew")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class CrewEntity {

  @Id
  @Column(name = "crew_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String contents;
  private String sns;
  private String region;
  private Integer imageId;
  private Integer logoId;
  private Boolean isDeleted;

  @OneToMany(mappedBy = "crewEntity")
  private Set<UserEntity> userEntities;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "imageId", insertable = false, updatable = false)
  private ObjectStorageEntity imageEntity;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "logoId", insertable = false, updatable = false)
  private ObjectStorageEntity logoEntity;
}
