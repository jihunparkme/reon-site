package com.site.reon.global.security.oauth2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OAuth2Client {

    KAKAO("Kakao", "kakao", "id"),
    GOOGLE("Google", "google", "sub"),
    APPLE("Apple", "apple", ""),
    EMPTY("", "", ""),
    ;

    private final String key;
    private final String registrationId;
    private final String nameAttributeName;

    public static OAuth2Client of(String registrationId) {
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

    public static void validateClientName(String registrationId) {
        if (isNotSupport(registrationId)) {
            throw new IllegalArgumentException("This is unsupported OAuth2 Client service. Please check authClientName field.");
        }
    }

    public boolean isKakaoLogin() {
        return KAKAO == this;
    }

    public boolean isEmpty() {
        return EMPTY == this;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
