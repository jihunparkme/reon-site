package com.site.reon.global.security.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppleOAuth2Token {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String iss;
    private String aud;
    private long exp;
    private long iat;
    private String sub;
    @JsonProperty("c_hash")
    private String cHash;
    private String email;
    @JsonProperty("email_verified")
    private String emailVerified;
    @JsonProperty("auth_time")
    private long authTime;
    @JsonProperty("nonce_supported")
    private boolean nonceSupported;

    public AppleOAuth2Token(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        try {
            AppleOAuth2Token appleOAuth2Token = objectMapper.readValue(payload, AppleOAuth2Token.class);
            this.iss = appleOAuth2Token.getIss();
            this.aud = appleOAuth2Token.getAud();
            this.exp = appleOAuth2Token.getExp();
            this.iat = appleOAuth2Token.getIat();
            this.sub = appleOAuth2Token.getSub();
            this.cHash = appleOAuth2Token.getCHash();
            this.email = appleOAuth2Token.getEmail();
            this.emailVerified = appleOAuth2Token.getEmailVerified();
            this.authTime = appleOAuth2Token.getAuthTime();
            this.nonceSupported = appleOAuth2Token.isNonceSupported();
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The token information is invalid.");
        }
    }
}
