package kr.borntorun.api.adapter.in.web.proxy;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.borntorun.api.core.converter.ObjectStorageConverter;
import kr.borntorun.api.core.service.ObjectStorageService;
import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.domain.port.model.ObjectStorage;
import kr.borntorun.api.domain.port.model.RemoveObjectStorageCommand;
import kr.borntorun.api.domain.port.model.UploadObjectStorageCommand;
import kr.borntorun.api.support.TokenDetail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ObjectStorageProxy {

	private final ObjectStorageConverter objectStorageConverter;

	private final ObjectStorageService objectStorageService;

	public ObjectStorage upload(TokenDetail my, Bucket bucket, MultipartFile file) {
		UploadObjectStorageCommand command = objectStorageConverter.toUploadObjectStorageCommand(my.getId(), file,
		  bucket);
		return objectStorageService.upload(command);
	}

	public void remove(TokenDetail my, Bucket bucket, long fileId) {
		RemoveObjectStorageCommand command = objectStorageConverter.toRemoveObjectStorageCommand(my, fileId, bucket);
		objectStorageService.remove(command);
	}
}