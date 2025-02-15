package kr.borntorun.api.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.borntorun.api.adapter.out.persistence.ObjectStorageRepository;
import kr.borntorun.api.adapter.out.thirdparty.ObjectStorageClient;
import kr.borntorun.api.adapter.out.thirdparty.model.Remove;
import kr.borntorun.api.adapter.out.thirdparty.model.RemoveAll;
import kr.borntorun.api.config.properties.MinioProperties;
import kr.borntorun.api.core.converter.ObjectStorageConverter;
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

  private final ObjectStorageClient objectStorageClient;
  private final MinioProperties minioProperties;
  private final ObjectStorageRepository objectStorageRepository;

  public ObjectStorageEntity upload(final UploadObjectStorageQuery query) {
    final String uploadedFileName = objectStorageClient.upload(
        ObjectStorageConverter.INSTANCE.toUpload(query));

    final String cdnUri = minioProperties.getCdnHost()
        + "/"
        + query.getBucketName()
        + "/"
        + uploadedFileName;

    return objectStorageRepository.save(
        ObjectStorageEntity.builder()
            .fileUri(cdnUri)
            .userId(query.myUserId())
            .build());

  }

  public List<ObjectStorageEntity> searchAll(final List<Integer> fileIds) {
    return objectStorageRepository.findAllById(fileIds);
  }

  public void remove(final RemoveObjectStorageQuery query) {
    if(0 == query.targetFileId()) {
      return;
    }

    ObjectStorageEntity objectStorage = objectStorageRepository.findById(query.targetFileId())
        .orElseThrow(() -> new NotFoundException("파일을 찾을 수 없습니다."));

    if(!query.my().getIsAdmin()) {
      if(query.my().getId() != objectStorage.getUserId()) {
        throw new InvalidException("본인이 올린 파일만 제거할 수 있습니다.");
      }
    }

    objectStorageRepository.deleteById(objectStorage.getId());

    objectStorageClient.remove(Remove.builder()
        .bucket(query.bucket().getBucketName())
        .objectName(objectStorage.getFileUri().substring(objectStorage.getFileUri().lastIndexOf("/") + 1))
        .build());
  }

  public void removeAll(final RemoveAllObjectStorageQuery query) {
    if(null == query.targetFileIds() || query.targetFileIds().isEmpty()) {
      return;
    }

    List<ObjectStorageEntity> objectStorages = objectStorageRepository.findAllById(query.targetFileIds());

    if(!query.my().getIsAdmin() && objectStorages.stream()
        .map(ObjectStorageEntity::getUserId)
        .noneMatch(e -> e == query.my().getId())) {
      throw new InvalidException("본인이 올린 파일만 제거할 수 있습니다.");
    }

    objectStorageRepository.saveAll(objectStorages);
    objectStorageRepository.deleteAllById(objectStorages.stream()
      .map(ObjectStorageEntity::getId)
      .toList());

    objectStorageClient.removeAll(RemoveAll.builder()
        .bucket(query.bucket())
        .objectNames(objectStorages.stream()
            .map(e -> e.getFileUri().substring(e.getFileUri().lastIndexOf("/") + 1))
            .collect(Collectors.toList()))
        .build());
  }

  public String modify(final ModifyObjectStorageQuery query) {
    ObjectStorageEntity objectStorage = objectStorageRepository.findById(query.getTargetFileId())
        .orElseThrow(() -> new NotFoundException("파일을 찾을 수 없습니다."));

    if(!query.getMy().getIsAdmin()) {
      if(query.getMy().getId() != objectStorage.getUserId()) {
        throw new InvalidException("본인이 올린 파일만 수정할 수 있습니다.");
      }
    }

    final String uploadedFileName = objectStorageClient.upload(ObjectStorageConverter.INSTANCE.toUpload(query));

    final String targetCdnUri = objectStorage.getFileUri();
    final String cdnUri = minioProperties.getCdnHost()
        + "/"
        + query.getBucket()
        + "/"
        + uploadedFileName;

    objectStorage.setFileUri(cdnUri);

    final Remove remove = ObjectStorageConverter.INSTANCE.toRemove(query);
    remove.setObjectName(targetCdnUri.substring(targetCdnUri.lastIndexOf("/" + 1)));

    objectStorageClient.remove(remove);

    return cdnUri;
  }
}
