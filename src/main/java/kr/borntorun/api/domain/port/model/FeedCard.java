package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCard {
  private long id;
  private List<String> imageUris;
  private String contents;
  private long viewQty;
  private long recommendationQty;
  private int commentQty;
  private LocalDateTime registeredAt;
  private Writer writer;
  private boolean hasRecommendation;
  private boolean hasComment;

  @Getter
  @Setter
  public static class Writer {
    private String userName;
    private String crewName;
    private String profileImageUri;
    private Boolean isAdmin;
    private Boolean isManager;
  }
}