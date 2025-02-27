package kr.borntorun.api.core.event.model;

import kr.borntorun.api.domain.constant.Bucket;

public record MinioRemoveEventModel(Bucket bucket, String objectName) {
}
