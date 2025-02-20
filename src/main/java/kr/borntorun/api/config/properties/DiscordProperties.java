package kr.borntorun.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@ConfigurationProperties("adapter.discord")
public class DiscordProperties {

	private final String host;
	private final String webhookPath;
	private final String contentType;
}
