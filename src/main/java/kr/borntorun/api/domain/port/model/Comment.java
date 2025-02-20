package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
	private long id;
	private long parentId;
	private int reCommentQty;
	private long feedId;
	private String contents;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
	private Writer writer;
	private Boolean isMyComment;

	public record Writer(
	  long userId,
	  String userName,
	  String profileImageUri,
	  String crewName,
	  Boolean isAdmin,
	  Boolean isManager
	) {
	}
}