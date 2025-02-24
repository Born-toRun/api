package kr.borntorun.api.infrastructure.model;

import org.springframework.web.multipart.MultipartFile;

import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.support.TokenDetail;

public record ModifyObjectStorageQuery(long targetFileId,
									   String cdnUri,
									   TokenDetail my,
									   MultipartFile file,
									   Bucket bucket) {
}
