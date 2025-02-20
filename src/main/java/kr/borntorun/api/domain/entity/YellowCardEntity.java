package kr.borntorun.api.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "yellow_card")
@NoArgsConstructor
@ToString
@Getter
@Setter
@IdClass(YellowCardMultiKey.class)
public class YellowCardEntity {

  @Id
  private long targetUserId;
  @Id
  private long sourceUserId;
  private String reason;
  private String basis;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "target_user_id")
  private UserEntity userEntity;
}
