package kr.borntorun.api.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.borntorun.api.core.converter.ObjectStorageConverter;
import kr.borntorun.api.domain.entity.ObjectStorageEntity;
import kr.borntorun.api.domain.port.ObjectStoragePort;
import kr.borntorun.api.domain.port.model.ObjectStorage;
import kr.borntorun.api.domain.port.model.RemoveObjectStorageCommand;
import kr.borntorun.api.domain.port.model.UploadObjectStorageCommand;
import kr.borntorun.api.infrastructure.ObjectStorageGateway;
import kr.borntorun.api.infrastructure.model.RemoveObjectStorageQuery;
import kr.borntorun.api.infrastructure.model.UploadObjectStorageQuery;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ObjectStorageService implements ObjectStoragePort {

  private final ObjectStorageGateway objectStorageGateway;

  @Transactional
  @Override
  public ObjectStorage upload(final UploadObjectStorageCommand command) {
    UploadObjectStorageQuery query = ObjectStorageConverter.INSTANCE.toUploadObjectStorageQuery(command);
    ObjectStorageEntity uploaded = objectStorageGateway.upload(query);
    return ObjectStorageConverter.INSTANCE.toObjectStorage(uploaded);

  }

  @Transactional
  @Override
  public void remove(final RemoveObjectStorageCommand command) {
    RemoveObjectStorageQuery query = ObjectStorageConverter.INSTANCE.toRemoveObjectStorageQuery(command);
    objectStorageGateway.remove(query);
  }
}