package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	@OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<CommentEntity> commentEntities = new HashSet<>();

	@OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<FeedImageMappingEntity> feedImageMappingEntities = new HashSet<>();

	@OneToMany(mappedBy = "feedEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<RecommendationEntity> recommendationEntities = new HashSet<>();

	public void add(List<FeedImageMappingEntity> feedImageMappingEntities) {
		for (FeedImageMappingEntity entity : feedImageMappingEntities) {
			entity.setFeedEntity(this);
		}

		this.feedImageMappingEntities.addAll(feedImageMappingEntities);
	}

	public void modify(List<FeedImageMappingEntity> feedImageMappingEntities) {
		for (FeedImageMappingEntity entity : feedImageMappingEntities) {
			entity.setFeedEntity(this);
		}

		this.feedImageMappingEntities.clear();
		this.feedImageMappingEntities.addAll(feedImageMappingEntities);
	}

	public void modify(ModifyFeedQuery query) {
		this.accessLevel = query.accessLevel();
		this.contents = query.contents();
		this.category = query.category();

		add(query.imageIds().stream()
		  .map(imageId -> FeedImageMappingEntity.builder()
			.imageId(imageId)
			.build())
		  .collect(Collectors.toList()));
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

	public boolean hasMyComment(long myUserId) {
		return commentEntities.stream()
		  .anyMatch(e -> e.getUserId() == myUserId);
	}

	public boolean hasMyRecommendation(long myUserId) {
		return recommendationEntities.stream()
		  .anyMatch(e -> e.getUserId() == myUserId);
	}
}