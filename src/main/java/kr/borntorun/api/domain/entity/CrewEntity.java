package kr.borntorun.api.domain.entity;

import java.util.Set;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "crew")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class CrewEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String contents;
	private String sns;
	private String region;
	@Setter
	private Long imageId;
	@Setter
	private Long logoId;

	@OneToMany(mappedBy = "crewEntity")
	private Set<UserEntity> userEntities;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private ObjectStorageEntity imageEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logoId", insertable = false, updatable = false)
	private ObjectStorageEntity logoEntity;
}
