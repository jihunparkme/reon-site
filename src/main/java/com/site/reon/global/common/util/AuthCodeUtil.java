package com.site.reon.global.common.util;

import java.util.Random;

public class AuthCodeUtil {
    private static final Random random = new Random();

    public static int generateAuthCode() {
        return random.nextInt(900000) + 100000;
    }

    public static String generateAuthCodeString() {
        return Integer.toString(generateAuthCode());
    }
}
