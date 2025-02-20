package kr.borntorun.api.adapter.out.thirdparty.model;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.BodyInserters.FormInserter;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthTokenRequest(@JsonProperty("grant_type") String grantType) {

	public FormInserter<String> getBody() {
		final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		body.add("grant_type", grantType);

		return BodyInserters.fromFormData(body);
	}
}
