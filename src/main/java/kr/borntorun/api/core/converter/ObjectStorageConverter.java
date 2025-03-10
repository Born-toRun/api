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

	UploadObjectStorageCommand toUploadObjectStorageCommand(final long myUserId, final MultipartFile file,
	  final Bucket bucket);

	RemoveObjectStorageCommand toRemoveObjectStorageCommand(final TokenDetail my, final long targetFileId,
	  final Bucket bucket);

	UploadObjectStorageQuery toUploadObjectStorageQuery(final UploadObjectStorageCommand source);

	@Mapping(target = "id", source = "id")
	ObjectStorage toObjectStorage(final ObjectStorageEntity source);

	Upload toUpload(final UploadObjectStorageQuery source);

	@Mapping(target = "myUserId", source = "my.id")
	Upload toUpload(final ModifyObjectStorageQuery source);

	RemoveObjectStorageQuery toRemoveObjectStorageQuery(final RemoveObjectStorageCommand source);

	@Mapping(target = "fileId", source = "id")
	UploadFileResponse toUploadFileResponse(final ObjectStorage source);

	Remove toRemove(final MinioRemoveEventModel source);

	RemoveAll toRemoveAll(final MinioRemoveAllEventModel source);
}
