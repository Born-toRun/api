package kr.borntorun.api.domain.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
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
  @Column(name = "mapping_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int feedId;
  private int imageId;
  private Boolean isDeleted;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feedId", insertable = false, updatable = false)
  private FeedEntity feedEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "imageId", insertable = false, updatable = false)
  private ObjectStorageEntity objectStorageEntity;

  public void remove() {
    this.isDeleted = true;
  }
}


