package kr.borntorun.api.core.event.model;

import java.util.List;

import kr.borntorun.api.domain.constant.Bucket;

public record MinioRemoveAllEventModel(Bucket bucket, List<String> objectNames) {
}
