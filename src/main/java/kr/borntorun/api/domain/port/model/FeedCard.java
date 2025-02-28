package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;
import java.util.List;

public record FeedCard(long id,
					   List<String> imageUris,
					   String contents,
					   long viewQty,
					   long recommendationQty,
					   int commentQty,
					   LocalDateTime registeredAt,
					   Writer writer,
					   boolean hasRecommendation,
					   boolean hasComment) {

	public record Writer(String userName,
						 String crewName,
						 String profileImageUri,
						 Boolean isAdmin,
						 Boolean isManager) {
	}
}