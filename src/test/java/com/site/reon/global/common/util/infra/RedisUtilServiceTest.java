package com.site.reon.global.common.util.infra;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class RedisUtilServiceTest {
    
    @Autowired
    RedisUtilService redisUtilService;

    private String key = "test-key";
    private String value = "test-value";

    @AfterEach
    void afterEach() {
        redisUtilService.deleteOf(key);
    }

    @Test
    void when_set_value_then_get_value_success() throws Exception {
        redisUtilService.setValue(key, value);

        Optional<String> resultOpt = redisUtilService.getValueOf(key);

        assertTrue(resultOpt.isPresent());
        assertEquals("test-value", resultOpt.get());
    }

    @Test
    void when_key_is_not_exist_then_return_null() throws Exception {
        Optional<String> resultOpt = redisUtilService.getValueOf(key);

        assertTrue(resultOpt.isEmpty());
    }

    @Test
    void setValueExpire() throws Exception {
        redisUtilService.setValueExpire(key, value, 5L);

        Optional<String> before5sec = redisUtilService.getValueOf(key);
        assertTrue(before5sec.isPresent());
        assertEquals("test-value", before5sec.get());

        log.info("sleep 5 seconds.");
        Thread.sleep(5000);

        Optional<String> after5sec = redisUtilService.getValueOf(key);
        assertTrue(after5sec.isEmpty());
    }
}