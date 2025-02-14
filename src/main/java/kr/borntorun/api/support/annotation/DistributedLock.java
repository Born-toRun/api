package kr.borntorun.api.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

  String key();
  TimeUnit timeUnit() default TimeUnit.SECONDS; // 락 시간 단위
  long waitTime() default 5L;   // 락 획득을 위해 waitTime(s) 만큼 대기
  long leaseTime() default 5L;  // 락을 획득한 이후 leaseTime(s) 이 지나면 락을 해제
}
