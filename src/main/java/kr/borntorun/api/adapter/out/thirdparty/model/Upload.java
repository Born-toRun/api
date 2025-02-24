package kr.borntorun.api.adapter.out.thirdparty.model;

import org.springframework.web.multipart.MultipartFile;

import kr.borntorun.api.domain.constant.Bucket;

public record Upload(MultipartFile file,
					 long myUserId,
					 Bucket bucket) {
}
