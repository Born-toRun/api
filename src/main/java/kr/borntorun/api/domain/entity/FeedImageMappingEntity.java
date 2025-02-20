package kr.borntorun.api.domain.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "feed_image_mapping")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@DynamicInsert
@Builder
public class FeedImageMappingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long feedId;
	private long imageId;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private FeedEntity feedEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private ObjectStorageEntity objectStorageEntity;
}


