package kr.borntorun.api.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import kr.borntorun.api.config.properties.MinioProperties;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MinioProperties.class)
public class MinioClientConfig {

	private final MinioProperties minioProperties;

	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder()
		  .endpoint(minioProperties.getNode())
		  .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
		  .build();
	}
}