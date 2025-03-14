package kr.borntorun.api.core.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import kr.borntorun.api.adapter.in.web.payload.UploadFileResponse;
import kr.borntorun.api.adapter.out.thirdparty.model.Remove;
import kr.borntorun.api.adapter.out.thirdparty.model.RemoveAll;
import kr.borntorun.api.adapter.out.thirdparty.model.Upload;
import kr.borntorun.api.core.event.model.MinioRemoveAllEventModel;
import kr.borntorun.api.core.event.model.MinioRemoveEventModel;
import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.domain.entity.ObjectStorageEntity;
import kr.borntorun.api.domain.port.model.ObjectStorage;
import kr.borntorun.api.domain.port.model.RemoveObjectStorageCommand;
import kr.borntorun.api.domain.port.model.UploadObjectStorageCommand;
import kr.borntorun.api.infrastructure.model.ModifyObjectStorageQuery;
import kr.borntorun.api.infrastructure.model.RemoveObjectStorageQuery;
import kr.borntorun.api.infrastructure.model.UploadObjectStorageQuery;
import kr.borntorun.api.support.TokenDetail;

@Mapper(componentModel = "spring")
public interface ObjectStorageConverter {

	UploadObjectStorageCommand toUploadObjectStorageCommand(long myUserId, MultipartFile file,
	  Bucket bucket);

	RemoveObjectStorageCommand toRemoveObjectStorageCommand(TokenDetail my, long targetFileId,
	  Bucket bucket);

	UploadObjectStorageQuery toUploadObjectStorageQuery(UploadObjectStorageCommand source);

	@Mapping(target = "id", source = "id")
	ObjectStorage toObjectStorage(ObjectStorageEntity source);

	Upload toUpload(UploadObjectStorageQuery source);

	@Mapping(target = "myUserId", source = "my.id")
	Upload toUpload(ModifyObjectStorageQuery source);

	RemoveObjectStorageQuery toRemoveObjectStorageQuery(RemoveObjectStorageCommand source);

	@Mapping(target = "fileId", source = "id")
	UploadFileResponse toUploadFileResponse(ObjectStorage source);

	Remove toRemove(MinioRemoveEventModel source);

	RemoveAll toRemoveAll(MinioRemoveAllEventModel source);
}
