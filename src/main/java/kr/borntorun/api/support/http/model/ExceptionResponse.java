package kr.borntorun.api.support.http.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {

	private String message;
}
