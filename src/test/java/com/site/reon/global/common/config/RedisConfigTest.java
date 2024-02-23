package com.site.reon.global.common.config;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @Disabled
    void when_save_and_read_then_success() {
        final ValueOperations<String, Object> stringOps = redisTemplate.opsForValue();
        stringOps.set("testKey", "testValue");

        assertThat(stringOps.get("testKey")).isEqualTo("testValue");
    }
}