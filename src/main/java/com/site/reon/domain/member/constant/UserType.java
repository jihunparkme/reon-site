package com.site.reon.domain.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    PRIVATE("PRIVATE"),
    COMPANY("COMPANY"),
    ;

    private final String type;
}

