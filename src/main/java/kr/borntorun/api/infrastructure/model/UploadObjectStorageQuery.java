package kr.borntorun.api.infrastructure.model;

import org.springframework.web.multipart.MultipartFile;

import kr.borntorun.api.domain.constant.Bucket;

public record UploadObjectStorageQuery(long myUserId,
									   MultipartFile file,
									   Bucket bucket) {

	public String getBucketName() {
		return bucket.getBucketName();
	}
}
