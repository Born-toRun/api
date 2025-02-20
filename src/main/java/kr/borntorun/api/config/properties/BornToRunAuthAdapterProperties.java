package kr.borntorun.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@ConfigurationProperties("adapter.borntorun-auth")
public class BornToRunAuthAdapterProperties {

	private final String host;
	private final String clientId;
	private final String clientSecret;
	private final String grantType;
	private final String signInPath;
	private final String authTokenPath;
	private final String refreshTokenPath;
}
