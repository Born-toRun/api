package kr.borntorun.api.infrastructure.model;

import kr.borntorun.api.domain.constant.Bucket;
import kr.borntorun.api.support.TokenDetail;

public record RemoveObjectStorageQuery(TokenDetail my,
									   long targetFileId,
									   Bucket bucket) {
}
