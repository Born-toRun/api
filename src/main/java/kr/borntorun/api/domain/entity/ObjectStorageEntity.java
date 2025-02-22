package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long userId;
	private String fileUri;
	private LocalDateTime uploadAt;

	@OneToMany(mappedBy = "objectStorageEntity", cascade = CascadeType.REMOVE)
	private Set<FeedImageMappingEntity> feedImageMappingEntities = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	private UserEntity userEntity;

	@OneToOne(mappedBy = "imageEntity", cascade = CascadeType.REMOVE)
	private CrewEntity crewImageEntity;

	@OneToOne(mappedBy = "logoEntity", cascade = CascadeType.REMOVE)
	private CrewEntity crewLogoEntity;
}
