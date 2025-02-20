package kr.borntorun.api.domain.port.model;

import java.time.LocalDateTime;

public record MarathonDetail(long id,
							 String title,
							 String owner,
							 String email,
							 String schedule,
							 String contact,
							 String course,
							 String location,
							 String venue,
							 String host,
							 String duration,
							 String homepage,
							 String venueDetail,
							 String remark,
							 LocalDateTime registeredAt,
							 Boolean isBookmarking) {
}
