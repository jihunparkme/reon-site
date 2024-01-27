package com.site.reon.global.security.oauth2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SocialLogin {

    KAKAO("Kakao", "kakao", "id"),
    GOOGLE("Google", "google", "sub"),
    APPLE("Apple", "", ""),
    EMPTY("", "", ""),
    ;

    private final String key;
    private final String registrationId;
    private final String nameAttributeName;

    public static SocialLogin of(String registrationId) {
        return Arrays.stream(values())
                .filter(social -> social.registrationId.equals(registrationId))
                .findFirst()
                .orElseGet(() -> EMPTY);
    }

    public static boolean isKakaoLogin(String registrationId) {
        return KAKAO == of(registrationId);
    }

    public static boolean isNotSupport(String registrationId) {
        return EMPTY == of(registrationId);
    }
}
