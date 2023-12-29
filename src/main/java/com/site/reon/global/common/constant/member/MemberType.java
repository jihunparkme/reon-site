package com.site.reon.global.common.constant.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {
    PRIVATE("PRIVATE"),
    COMPANY("COMPANY"),
    ;

    private final String type;
}

