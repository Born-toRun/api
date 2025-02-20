package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long parentId;
	private long userId;
	private long feedId;
	private String contents;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private UserEntity userEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private FeedEntity feedEntity;

	@OneToMany(mappedBy = "commentContentsEntity", cascade = CascadeType.REMOVE)
	private Set<RecommendationEntity> recommendationEntities;

	public long getRecommendationQty() {
		return recommendationEntities.stream()
		  .filter(e -> e.getRecommendationType().equals(RecommendationType.FEED))
		  .count();
	}

	public boolean isRootComment() {
		return parentId == 0;
	}
}


