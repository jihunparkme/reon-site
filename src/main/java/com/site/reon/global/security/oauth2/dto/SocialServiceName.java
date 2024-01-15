package com.site.reon.global.security.oauth2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialServiceName {

    KAKAO("Kakao"),
    GOOGLE("Google"),
    APPLE("Apple"),
    ;

    private final String key;
}
