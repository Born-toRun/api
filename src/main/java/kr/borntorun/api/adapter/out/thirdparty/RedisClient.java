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

  public void save(final String key, final Object value) {
    redissonClient.getBucket(key)
        .set(value);
  }

  public void save(final String key, final Object value, final Duration duration) {
    redissonClient.getBucket(key)
        .set(value, duration);
  }

  public void remove(final String key) {
    redissonClient.getBucket(key)
        .delete();
  }

  public boolean exist(final String key) {
    return redissonClient.getBucket(key)
        .isExists();
  }

  public Object get(final String key) {
    return redissonClient.getBucket(key)
        .get();
  }

  public Object getAndRemove(final String key) {
    return redissonClient.getBucket(key)
        .getAndDelete();
  }

  public void add(final String key, final String value) {
    redissonClient.getList(key).add(value);
  }

  public void removeAll(final String key) {
    redissonClient.getList(key).clear();
  }

  public void removeValue(final String key, final String value) {
    redissonClient.getList(key).remove(value);
  }

  public List<Object> getList(final String key) {
    return redissonClient.getList(key).readAll();
  }
}
