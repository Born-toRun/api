package kr.borntorun.api.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.spring.cache.HazelcastCacheManager;

@EnableCaching
@Configuration
public class CachingConfig {

	@Bean
	public CacheManagerCustomizer<HazelcastCacheManager> cacheManagerCustomizer() {
		return cacheManagerCustomizer -> {
		};
	}
}