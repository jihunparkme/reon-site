package com.site.reon.global.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthCodeUtilTest {
    @Test
    void generateAuthCode() throws Exception {
        int authCode = AuthCodeUtil.generateAuthCode();
        System.out.println(authCode);
        assertTrue(authCode >= 100000 && authCode <= 999999);
    }
}