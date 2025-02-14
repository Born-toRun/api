package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.ObjectStorageConverter;
import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.domain.entity.ObjectStorageEntity;
import kr.borntorun.api.domain.port.ObjectStoragePort;
import kr.borntorun.api.domain.port.model.ObjectStorage;
import kr.borntorun.api.domain.port.model.RemoveObjectStorageCommand;
import kr.borntorun.api.domain.port.model.UploadObjectStorageCommand;
import kr.borntorun.api.infrastructure.FeedImageMappingGateway;
import kr.borntorun.api.infrastructure.ObjectStorageGateway;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ObjectStorageService implements ObjectStoragePort {

  private final ObjectStorageGateway objectStorageGateway;
  private final FeedImageMappingGateway feedImageMappingGateway;

  @Transactional
  @Override
  public ObjectStorage upload(final UploadObjectStorageCommand command) {
    ObjectStorageEntity uploaded = objectStorageGateway.upload(ObjectStorageConverter.INSTANCE.toUploadObjectStorageQuery(command));
    return ObjectStorageConverter.INSTANCE.toObjectStorage(uploaded);

  }

  @Transactional
  @Override
  public void remove(final RemoveObjectStorageCommand command) {
    if(Bucket.FEED.equals(command.bucket())) {
      feedImageMappingGateway.remove(command.targetFileId());
    }
    objectStorageGateway.remove(ObjectStorageConverter.INSTANCE.toRemoveObjectStorageQuery(command));
  }
}