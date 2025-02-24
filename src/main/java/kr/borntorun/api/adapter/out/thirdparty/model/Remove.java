package kr.borntorun.api.adapter.out.thirdparty.model;

import kr.borntorun.api.domain.constant.Bucket;

public record Remove(Bucket bucket,
					 String objectName) {
}
