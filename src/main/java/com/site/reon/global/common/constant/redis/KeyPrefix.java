package com.site.reon.global.common.constant.redis;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KeyPrefix {
    SIGN_UP("sign-up:", "sign up", "sign-up-verified:"),
    WITHDRAW("withdraw:", "withdrawal", "GOOGLE"),
    ;

    private final String prefix;
    private final String purpose;
    private final String verifyPrefix;

    public String prefix() {
        return prefix;
    }

    public String purpose() {
        return purpose;
    }

    public String verifyPrefix() {
        return verifyPrefix;
    }
}
