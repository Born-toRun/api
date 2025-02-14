package kr.borntorun.api.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import kr.borntorun.api.config.properties.BornToRunAuthAdapterProperties;
import kr.borntorun.api.config.properties.DiscordAdapterProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({DiscordAdapterProperties.class, BornToRunAuthAdapterProperties.class})
public class WebClientConfig {

  private final DiscordAdapterProperties discordAdapterProperties;
  private final BornToRunAuthAdapterProperties bornToRunAuthAdapterProperties;
  private final WebClient.Builder webClientBuilder;

  @Bean
  public WebClient discordConnector() {
    return webClientBuilder.baseUrl(discordAdapterProperties.getHost())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, discordAdapterProperties.getContentType())
        .build();
  }

  @Bean
  public WebClient bornToRunAuthConnector() {
    return webClientBuilder.baseUrl(bornToRunAuthAdapterProperties.getHost())
        .build();
  }
}
