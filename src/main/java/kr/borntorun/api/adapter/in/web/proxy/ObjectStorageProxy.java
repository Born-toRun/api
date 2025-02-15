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

  private final ObjectStorageService objectStorageService;

  public ObjectStorage upload(final TokenDetail my, final Bucket bucket, final MultipartFile file) {
    UploadObjectStorageCommand command = ObjectStorageConverter.INSTANCE.toUploadObjectStorageCommand(my.getId(), file, bucket);
    return objectStorageService.upload(command);
  }

  public void remove(final TokenDetail my, final Bucket bucket, final int fileId) {
    RemoveObjectStorageCommand command = ObjectStorageConverter.INSTANCE.toRemoveObjectStorageCommand(my, fileId, bucket);
    objectStorageService.remove(command);
  }
}