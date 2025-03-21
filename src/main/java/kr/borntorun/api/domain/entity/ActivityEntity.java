package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.ObjectUtils;

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
import kr.borntorun.api.infrastructure.model.ModifyActivityQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "activity")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@DynamicInsert
public class ActivityEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String contents;
	private LocalDateTime startDate;
	private String venue;
	private String venueUrl;
	private int participantsLimit;
	private int participationFee;
	private String course;
	private String courseDetail;
	private String path;
	private Long userId;
	private Boolean isOpen;
	private LocalDateTime updatedAt;
	private LocalDateTime registeredAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserEntity userEntity;

	@OneToMany(mappedBy = "activityEntity", cascade = CascadeType.REMOVE)
	private Set<ActivityParticipationEntity> activityParticipationEntities = new HashSet<>();

	public void open() {
		this.isOpen = true;
	}

	public void modify(ModifyActivityQuery query) {
		if (!ObjectUtils.isEmpty(query.title())) {
			this.title = query.title();
		}
		if (!ObjectUtils.isEmpty(query.contents())) {
			this.contents = query.contents();
		}
		if (!ObjectUtils.isEmpty(query.startDate())) {
			this.startDate = query.startDate();
		}
		if (!ObjectUtils.isEmpty(query.venue())) {
			this.venue = query.venue();
		}
		if (!ObjectUtils.isEmpty(query.venueUrl())) {
			this.venueUrl = query.venueUrl();
		}
		if (!ObjectUtils.isEmpty(query.participantsLimit())) {
			this.participantsLimit = query.participantsLimit();
		}
		if (!ObjectUtils.isEmpty(query.participationFee())) {
			this.participationFee = query.participationFee();
		}
		if (!ObjectUtils.isEmpty(query.course())) {
			this.course = query.course();
		}
		if (!ObjectUtils.isEmpty(query.courseDetail())) {
			this.courseDetail = query.courseDetail();
		}
		if (!ObjectUtils.isEmpty(query.path())) {
			this.path = query.path();
		}
	}
}


