package kr.borntorun.api.core.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import kr.borntorun.api.core.converter.ObjectStorageConverter;
import kr.borntorun.api.core.event.model.MinioRemoveAllEventModel;
import kr.borntorun.api.core.event.model.MinioRemoveEventModel;
import kr.borntorun.api.infrastructure.MinioGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MinioEventHandler {

	private final MinioGateway minioGateway;

	private final ObjectStorageConverter objectStorageConverter;

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void onObjectStorageRemoved(MinioRemoveEventModel event) {
		log.info("{} Bucket에서 {} 파일을 제거합니다.", event.bucket().getBucketName(), event.objectName());
		minioGateway.removeObject(objectStorageConverter.toRemove(event));
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void onObjectStorageRemoved(MinioRemoveAllEventModel event) {
		log.info("{} Bucket에서 {} 파일들을 제거합니다.", event.bucket().getBucketName(), event.objectNames());
		minioGateway.removeObjects(objectStorageConverter.toRemoveAll(event));
	}
}
