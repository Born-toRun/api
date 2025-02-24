package kr.borntorun.api.adapter.out.thirdparty.model;

import java.util.List;

import kr.borntorun.api.domain.constant.Bucket;

public record RemoveAll(Bucket bucket,
						List<String> objectNames) {
}
