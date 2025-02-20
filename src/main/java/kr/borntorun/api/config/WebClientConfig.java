package kr.borntorun.api.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import kr.borntorun.api.config.properties.BornToRunAuthAdapterProperties;
import kr.borntorun.api.config.properties.DiscordProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({DiscordProperties.class, BornToRunAuthAdapterProperties.class})
public class WebClientConfig {

  private final DiscordProperties discordProperties;
  private final BornToRunAuthAdapterProperties bornToRunAuthAdapterProperties;
  private final WebClient.Builder webClientBuilder;

  @Bean
  public WebClient discordConnector() {
    return webClientBuilder.baseUrl(discordProperties.getHost())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, discordProperties.getContentType())
        .build();
  }

  @Bean
  public WebClient bornToRunAuthConnector() {
    return webClientBuilder.baseUrl(bornToRunAuthAdapterProperties.getHost())
        .build();
  }
}
