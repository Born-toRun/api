package kr.borntorun.api.domain.port.model;

public record Marathon(long id,
					   String title,
					   String schedule,
					   String venue,
					   String course,
					   Boolean isBookmarking) {
}
