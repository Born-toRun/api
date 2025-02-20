package kr.borntorun.api.adapter.out.thirdparty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import kr.borntorun.api.config.properties.DiscordProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(DiscordProperties.class)
public class DiscordAdapter {

  private static final int MESSAGE_MAX_SIZE = 2000;
  private static final String MESSAGE = "content";
  private final DiscordProperties discordProperties;
  private final WebClient discordConnector;

  @Async
  public void send(String message) {
    if(Objects.isNull(message) || message.isEmpty()) {
      return;
    }

    log.info("discord error messange: {}", message);
    List<String> slicedMessage = new ArrayList<>();

    int index = 0;

	  while (index < message.length()) {
		int length = Math.min(MESSAGE_MAX_SIZE, message.length() - index);

		int nextIndex = message.indexOf("\n", index);

		if (nextIndex != -1 && nextIndex == index + 1 && length > 10) {
		  length = MESSAGE_MAX_SIZE-1;
		}

		slicedMessage.add(message.substring(index, index + length));

		index += length;
	  }

	  try {
		for (String m : slicedMessage) {
		  m = m.trim();
		  if(m.isEmpty()) {
			continue;
		  }

		  final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		  body.add(MESSAGE, m);

		  discordConnector.post()
			  .uri(uriBuilder -> uriBuilder
				  .path(discordProperties.getWebhookPath())
				  .build())
			  .body(BodyInserters.fromFormData(body))
			  .retrieve()
			  .toEntity(Void.class)
			  .delayElement(Duration.ofSeconds(1))
			  .block();
		}
	  } catch (Exception e) {
		log.error ("Discord notify failed", e);
	  }
  }
}
