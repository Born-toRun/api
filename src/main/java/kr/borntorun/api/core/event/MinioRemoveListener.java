package kr.borntorun.api.core.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreRemove;
import kr.borntorun.api.core.event.model.MinioRemoveEventModel;
import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.domain.entity.ObjectStorageEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MinioRemoveListener {

	private final ApplicationEventPublisher eventPublisher;

	@PreRemove
	public void onPreRemove(ObjectStorageEntity objectStorageEntity) {
		if (objectStorageEntity.getFileUri() != null) {
			eventPublisher.publishEvent(
			  new MinioRemoveEventModel(Bucket.valueOf(objectStorageEntity.getBucketName().toUpperCase()),
				objectStorageEntity.getFileUri().substring(objectStorageEntity.getFileUri().lastIndexOf("/") + 1)));
		}
	}
}
