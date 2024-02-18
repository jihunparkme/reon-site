package com.site.reon.global.common.util.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisUtilService {
    private final StringRedisTemplate redisTemplate;

    public Optional<String> getValueOf(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    public void setValue(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setValueExpire(String key, String value, long seconds) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(seconds);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteOf(String key) {
        redisTemplate.delete(key);
    }
}
