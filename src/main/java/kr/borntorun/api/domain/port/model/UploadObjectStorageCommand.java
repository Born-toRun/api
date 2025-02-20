package kr.borntorun.api.domain.port.model;

import org.springframework.web.multipart.MultipartFile;

import kr.borntorun.api.domain.constant.Bucket;

public record UploadObjectStorageCommand(long myUserId,
										 MultipartFile file,
										 Bucket bucket) {

}
