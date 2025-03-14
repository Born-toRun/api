package kr.borntorun.api.infrastructure;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.ObjectStorageRepository;
import kr.borntorun.api.config.properties.MinioProperties;
import kr.borntorun.api.core.converter.ObjectStorageConverter;
import kr.borntorun.api.core.event.model.MinioRemoveAllEventModel;
import kr.borntorun.api.core.event.model.MinioRemoveEventModel;
import kr.borntorun.api.domain.entity.ObjectStorageEntity;
import kr.borntorun.api.infrastructure.model.ModifyObjectStorageQuery;
import kr.borntorun.api.infrastructure.model.RemoveAllObjectStorageQuery;
import kr.borntorun.api.infrastructure.model.RemoveObjectStorageQuery;
import kr.borntorun.api.infrastructure.model.UploadObjectStorageQuery;
import kr.borntorun.api.support.exception.InvalidException;
import kr.borntorun.api.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObjectStorageGateway {

	private final ObjectStorageConverter objectStorageConverter;
	private final MinioProperties minioProperties;
	private final ObjectStorageRepository objectStorageRepository;
	private final MinioGateway minioGateway;
	private final ApplicationEventPublisher eventPublisher;

	public ObjectStorageEntity upload(UploadObjectStorageQuery query) {
		String uploadedFileName = minioGateway.uploadObject(objectStorageConverter.toUpload(query));

		String cdnUri = minioProperties.getCdnHost()
		  + "/"
		  + query.getBucketName()
		  + "/"
		  + uploadedFileName;

		return objectStorageRepository.save(
		  ObjectStorageEntity.builder()
			.fileUri(cdnUri)
			.userId(query.myUserId())
			.bucketName(query.getBucketName())
			.build());

	}

	public ObjectStorageEntity search(long id) {
		return objectStorageRepository.findById(id)
		  .orElseThrow(() -> new NotFoundException("파일을 찾을 수 없습니다."));
	}

	public List<ObjectStorageEntity> searchAll(List<Long> fileIds) {
		return objectStorageRepository.findAllById(fileIds);
	}

	public void remove(RemoveObjectStorageQuery query) {
		if (0 == query.targetFileId()) {
			return;
		}

		ObjectStorageEntity objectStorage = search(query.targetFileId());

		if (!query.my().getIsAdmin()) {
			if (query.my().getId() != objectStorage.getUserId()) {
				throw new InvalidException("본인이 올린 파일만 제거할 수 있습니다.");
			}
		}

		objectStorageRepository.deleteById(objectStorage.getId());

		eventPublisher.publishEvent(new MinioRemoveEventModel(query.bucket(), objectStorage.getFileUri()
		  .substring(objectStorage.getFileUri()
			.lastIndexOf("/") + 1)));
	}

	public void removeAll(RemoveAllObjectStorageQuery query) {
		if (null == query.targetFileIds() || query.targetFileIds().isEmpty()) {
			return;
		}

		List<ObjectStorageEntity> objectStorages = objectStorageRepository.findAllById(query.targetFileIds());

		if (!query.my().getIsAdmin() && objectStorages.stream()
		  .map(ObjectStorageEntity::getUserId)
		  .noneMatch(e -> e == query.my().getId())) {
			throw new InvalidException("본인이 올린 파일만 제거할 수 있습니다.");
		}

		objectStorageRepository.deleteAllById(objectStorages.stream()
		  .map(ObjectStorageEntity::getId)
		  .toList());

		eventPublisher.publishEvent(new MinioRemoveAllEventModel(query.bucket(), objectStorages.stream()
		  .map(e -> e.getFileUri().substring(e.getFileUri().lastIndexOf("/") + 1))
		  .toList()));
	}

	public String modify(ModifyObjectStorageQuery query) {
		ObjectStorageEntity objectStorage = search(query.targetFileId());

		if (!query.my().getIsAdmin()) {
			if (query.my().getId() != objectStorage.getUserId()) {
				throw new InvalidException("본인이 올린 파일만 수정할 수 있습니다.");
			}
		}

		String uploadedFileName = minioGateway.uploadObject(objectStorageConverter.toUpload(query));
		String targetCdnUri = objectStorage.getFileUri();
		String cdnUri = minioProperties.getCdnHost()
		  + "/"
		  + query.bucket()
		  + "/"
		  + uploadedFileName;

		objectStorage.setFileUri(cdnUri);
		objectStorageRepository.save(objectStorage);

		eventPublisher.publishEvent(new MinioRemoveEventModel(query.bucket(),
		  targetCdnUri.substring(targetCdnUri.lastIndexOf("/") + 1)));

		return cdnUri;
	}
}
