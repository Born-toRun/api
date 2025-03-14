package kr.borntorun.api.adapter.out.thirdparty;

import java.time.Duration;
import java.util.List;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisClient {

	private final RedissonClient redissonClient;

	public void save(String key, Object value) {
		redissonClient.getBucket(key)
		  .set(value);
	}

	public void save(String key, Object value, Duration duration) {
		redissonClient.getBucket(key)
		  .set(value, duration);
	}

	public void remove(String key) {
		redissonClient.getBucket(key)
		  .delete();
	}

	public boolean exist(String key) {
		return redissonClient.getBucket(key)
		  .isExists();
	}

	public Object get(String key) {
		return redissonClient.getBucket(key)
		  .get();
	}

	public Object getAndRemove(String key) {
		return redissonClient.getBucket(key)
		  .getAndDelete();
	}

	public void add(String key, String value) {
		redissonClient.getList(key).add(value);
	}

	public void removeAll(String key) {
		redissonClient.getList(key).clear();
	}

	public void removeValue(String key, String value) {
		redissonClient.getList(key).remove(value);
	}

	public List<Object> getList(String key) {
		return redissonClient.getList(key).readAll();
	}
}
