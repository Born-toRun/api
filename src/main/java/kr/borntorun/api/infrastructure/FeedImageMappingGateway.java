package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.FeedImageMappingRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedImageMappingGateway {

	private final FeedImageMappingRepository feedImageMappingRepository;

	public void removeAllByFileId(List<Long> imageIds) {
		feedImageMappingRepository.deleteAllByImageIdIn(imageIds);
	}
}
