package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.borntorun.api.domain.constant.ProviderType;
import kr.borntorun.api.domain.constant.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// TODO: 캡슐화, 꼭 필요한 애노테이션만 쓰기!!!
// TODO: 모든 엔티티에 대해서 단방향/양방향에 따른 연관관계 처리 확인하기!!!
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
	private long id;
	private String socialId;
	@Enumerated(EnumType.STRING)
	private ProviderType providerType;
	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	private String name;
	private Long crewId;
	private String instagramId;
	private LocalDateTime lastLoginAt;
	private Long imageId;
	private int yellowCardQty;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crewId", insertable = false, updatable = false)
	private CrewEntity crewEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "imageId", insertable = false, updatable = false)
	private ObjectStorageEntity objectStorageEntity;

	@OneToOne(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
	private UserRefreshTokenEntity userRefreshTokenEntity;

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
	@Builder.Default
	private Set<FeedEntity> feedEntities = new HashSet<>();

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
	@Builder.Default
	private Set<ActivityEntity> activityEntities = new HashSet<>();

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
	@Builder.Default
	private Set<ActivityParticipationEntity> activityParticipationEntities = new HashSet<>();

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
	@Builder.Default
	private Set<CommentEntity> commentEntities = new HashSet<>();

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
	@Builder.Default
	private Set<MarathonBookmarkEntity> marathonBookmarkEntities = new HashSet<>();

	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
	@Builder.Default
	private Set<RecommendationEntity> recommendationEntities = new HashSet<>();

	@OneToOne(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
	private UserPrivacyEntity userPrivacyEntity;

	public String getProfileImageUri() {
		if (objectStorageEntity == null || objectStorageEntity.getId() == 0) {
			return null;
		}
		return objectStorageEntity.getFileUri();
	}

	public Boolean getIsAdmin() {
		return roleType.equals(RoleType.ADMIN);
	}

	public Boolean getIsManager() {
		return roleType.equals(RoleType.MANAGER);
	}

	public void modify(String instagramId, long profileImageId) {
		if (profileImageId != 0) {
			this.imageId = profileImageId;
		}

		if (StringUtils.hasLength(instagramId)) {
			this.instagramId = instagramId;
		}
	}

	public void modify(String userName, Long crewId, String instagramId) {
		if (crewId != 0) {
			this.crewId = crewId;
		}

		if (StringUtils.hasLength(instagramId)) {
			this.instagramId = instagramId;
		}

		if (StringUtils.hasLength(userName)) {
			this.name = userName;
		}
	}

	public void add(UserPrivacyEntity userPrivacyEntity) {
		this.userPrivacyEntity = userPrivacyEntity;
		userPrivacyEntity.setUserEntity(this);
	}
}
