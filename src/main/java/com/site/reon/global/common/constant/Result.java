package com.site.reon.global.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Result {
    FAIL("FAIL"),
    SUCCESS("SUCCESS");

    private final String message;
}
