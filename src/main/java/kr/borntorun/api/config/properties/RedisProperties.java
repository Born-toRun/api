package kr.borntorun.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@ConfigurationProperties("adapter.redis")
public class RedisProperties {

  private String host;
  private int port;
  private String password;
  private int timeout;
}