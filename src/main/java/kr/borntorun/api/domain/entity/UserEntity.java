package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import jakarta.persistence.CascadeType;
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
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Builder
@ToString
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String socialId;
  private String ageRange;
  private String name;
  private int crewId;
  private String birthday;
  private String gender;
  private String instagramId;
  private LocalDateTime lastLoginAt;
  private int imageId;
  private int yellowCardQty;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id", insertable = false, updatable = false)
  private CrewEntity crewEntity;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<FeedEntity> feedEntities;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<ActivityEntity> activityEntities;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<ActivityParticipationEntity> activityParticipationEntities;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<AuthorityEntity> authorityEntities;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<CommentEntity> commentEntities;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<MarathonBookmarkEntity> marathonBookmarkEntities;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<ObjectStorageEntity> objectStorageEntities;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<RecommendationEntity> recommendationEntities;

  @OneToOne(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private UserPrivacyEntity userPrivacyEntity;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
  private Set<YellowCardEntity> yellowCardEntities;

  public String getProfileImageUri() {
    return objectStorageEntities.stream()
        .findFirst()
        .orElse(ObjectStorageEntity.defaultEntity())
        .getFileUri();
  }

  public Boolean getIsAdmin() {
    return authorityEntities.stream()
        .anyMatch(e -> e.getAuthority().equals("ADMIN"));
  }

  public Boolean getIsManager() {
    return authorityEntities.stream()
        .anyMatch(e -> e.getAuthority().equals("MANAGER"));
  }

  public void modify(String instagramId, int profileImageId) {
    if(profileImageId != 0) {
      this.imageId = profileImageId;
    }

    if(StringUtils.hasLength(instagramId)) {
      this.instagramId = instagramId;
    }
  }

  public void modify(String userName, int crewId, String instagramId) {
    if(crewId != 0) {
      this.crewId = crewId;
    }

    if(StringUtils.hasLength(instagramId)) {
      this.instagramId = instagramId;
    }

    if(StringUtils.hasLength(userName)) {
      this.name = userName;
    }
  }
}
