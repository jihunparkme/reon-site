package com.site.reon.global.security.oauth2.dto;

import com.site.reon.aggregate.member.query.dto.AppleOAuth2Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppleOAuth2TokenTest {

    private final static String APPLE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiYWFyb24ucGFyayIsImV4cCI6MTcwNjQ0NTUyOSwiaWF0IjoxNzA2MzU5MTI5LCJzdWIiOiIwMDAzODUuMDQ3c2dmNjZhYnM2NGQ2MGE0MDZkNWQ0YjNiNHgydjIuMTk5MyIsImNfaGFzaCI6IkYyWWRiN0R2RUJZaE9vUElHdGhEb0ciLCJlbWFpbCI6InVzZXJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNzA2MzU5MTI5LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.8DWNWY3PkDRdXzAjmrcaWH9p0tvjmg3ieOH4MZXz7Gs";

    @Test
    void AppleOAuth2Token() throws Exception {
        AppleOAuth2Token appleOAuth2Token = new AppleOAuth2Token(APPLE_TOKEN);
        Assertions.assertEquals("https://appleid.apple.com", appleOAuth2Token.getIss());
        Assertions.assertEquals("aaron.park", appleOAuth2Token.getAud());
        Assertions.assertEquals(1706445529, appleOAuth2Token.getExp());
        Assertions.assertEquals(1706359129, appleOAuth2Token.getIat());
        Assertions.assertEquals("000385.047sgf66abs64d60a406d5d4b3b4x2v2.1993", appleOAuth2Token.getSub());
        Assertions.assertEquals("F2Ydb7DvEBYhOoPIGthDoG", appleOAuth2Token.getCHash());
        Assertions.assertEquals("user@gmail.com", appleOAuth2Token.getEmail());
        Assertions.assertEquals("true", appleOAuth2Token.getEmailVerified());
        Assertions.assertEquals(1706359129, appleOAuth2Token.getAuthTime());
        Assertions.assertEquals(true, appleOAuth2Token.isNonceSupported());
    }
}