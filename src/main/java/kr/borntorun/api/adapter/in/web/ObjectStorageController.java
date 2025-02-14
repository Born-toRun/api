package kr.borntorun.api.adapter.in.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.borntorun.api.adapter.in.web.payload.UploadFileResponse;
import kr.borntorun.api.adapter.in.web.proxy.ObjectStorageProxy;
import kr.borntorun.api.core.converter.ObjectStorageConverter;
import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.domain.port.model.ObjectStorage;
import kr.borntorun.api.support.TokenDetail;
import kr.borntorun.api.support.annotation.AuthUser;
import lombok.RequiredArgsConstructor;

@Tag(name = "Object Storage 파일 업로드/다운로드", description = "file api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/object-storage")
public class ObjectStorageController {

  private final ObjectStorageProxy objectStorageProxy;

  @Operation(summary = "파일 업로드", description = "파일을 업로드합니다.")
  @RequestMapping(value = "/{bucket}", method= RequestMethod.POST, produces="application/json;charset=UTF-8", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UploadFileResponse> removeFile(@AuthUser TokenDetail my, @PathVariable Bucket bucket, @RequestParam(value = "file") MultipartFile file) {
    final ObjectStorage objectStorage = objectStorageProxy.upload(my, bucket, file);

    return ResponseEntity.ok(ObjectStorageConverter.INSTANCE.toUploadFileResponse(objectStorage));
  }

  @Operation(summary = "파일 삭제", description = "파일을 삭제합니다.")
  @RequestMapping(value = "/{bucket}/{fileId}", method= RequestMethod.DELETE)
  public void removeFile(@AuthUser TokenDetail my, @PathVariable Bucket bucket, @PathVariable int fileId) {
    objectStorageProxy.remove(my, bucket, fileId);
  }
}


