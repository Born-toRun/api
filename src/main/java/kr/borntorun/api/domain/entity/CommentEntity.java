package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	private Long parentId;
	private Long userId;
	private Long feedId;
	private String contents;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserEntity userEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feedId", insertable = false, updatable = false)
	private FeedEntity feedEntity;

	@OneToMany(mappedBy = "commentEntity", cascade = CascadeType.REMOVE)
	private Set<RecommendationEntity> recommendationEntities = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId", insertable = false, updatable = false)
	private CommentEntity parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentEntity> child = new ArrayList<>();

	public void addParent(CommentEntity parent) {
		this.parent = parent;
		if (parent != null) {
			parent.getChild().add(this);
		}
	}

	public long getRecommendationQty() {
		return recommendationEntities.stream()
		  .filter(e -> e.getRecommendationType().equals(RecommendationType.FEED))
		  .count();
	}

	public boolean isRootComment() {
		return parentId == null;
	}
}


