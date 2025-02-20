package kr.borntorun.api.infrastructure.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BookmarkMarathonQuery {
	private long marathonId;
	private long myUserId;
}
