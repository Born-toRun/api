package kr.borntorun.api.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.borntorun.api.config.properties.RedisProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfig {

	private static final String SCHEME = "redis://";
	private final RedisProperties redisProperties;

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer()
		  .setAddress(SCHEME + redisProperties.getHost() + ":" + redisProperties.getPort())
		  .setPassword(redisProperties.getPassword())
		  .setTimeout(redisProperties.getTimeout());

		return Redisson.create(config);
	}
}
