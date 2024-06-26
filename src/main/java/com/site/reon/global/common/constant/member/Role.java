package com.site.reon.global.common.constant.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "GUEST"),
    USER("ROLE_USER", "USER"),
    ADMIN("ROLE_ADMIN", "ADMIN"),
    PILOT("ROLE_PILOT", "PILOT"),
    ;

    private final String key;
    private final String title;

    public String key() {
        return key;
    }

    public String title() {
        return title;
    }
}
