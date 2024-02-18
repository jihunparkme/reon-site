package com.site.reon.global.common.constant.redis;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KeyPrefix {
    SIGN_UP("sign-up:"),
    ;

    private final String prefix;

    public String prefix() {
        return prefix;
    }
}
