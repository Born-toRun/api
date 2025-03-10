package kr.borntorun.api.domain.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_privacy")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@DynamicInsert
public class UserPrivacyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Long userId;
	private Boolean isInstagramIdPublic;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", insertable = false, updatable = false)
	@Setter
	private UserEntity userEntity;

	public void change(boolean isInstagramIdPublic) {
		this.isInstagramIdPublic = isInstagramIdPublic;
	}
}
