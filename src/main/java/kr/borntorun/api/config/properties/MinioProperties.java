package kr.borntorun.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@ConfigurationProperties("adapter.objectstorage.minio")
public class MinioProperties {

  private final String node;
  private final String accessKey;
  private final String secretKey;
  private final String cdnHost;
}