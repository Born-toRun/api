package kr.borntorun.api.domain.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "marathon")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class MarathonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String title;
  private String owner;
  private String email;
  private String schedule;
  private String contact;
  private String course;
  private String location;
  private String venue;
  private String host;
  private String duration;
  private String homepage;
  private String venueDetail;
  private String remark;
  private LocalDateTime registeredAt;

  @OneToMany(mappedBy = "marathonEntity", cascade = CascadeType.REMOVE)
  private Set<MarathonBookmarkEntity> marathonBookmarkEntities;
}


