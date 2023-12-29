package com.site.reon.aggregate.member.constant;

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

