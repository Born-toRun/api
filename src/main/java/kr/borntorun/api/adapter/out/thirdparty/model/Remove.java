package kr.borntorun.api.adapter.out.thirdparty.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Remove {
	private String bucket;
	private String objectName;
}
