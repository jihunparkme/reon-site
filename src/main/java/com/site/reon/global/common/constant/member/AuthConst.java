package com.site.reon.global.common.constant.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AuthConst {
    AUTHORIZATION_HEADER("Authorization"),
    SET_COOKIE("Set-Cookie"),
    ACCESS_TOKEN("Access-Token"),
    NONE("None"),
    ;

    private String key;

    public String key() {
        return key;
    }
}
