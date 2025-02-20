package kr.borntorun.api.adapter.out.thirdparty.model;

import java.util.List;

import kr.borntorun.api.domain.constant.Bucket;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class RemoveAll {
	private Bucket bucket;
	@Setter
	private List<String> objectNames;
}
