package com.site.reon.aggregate.member.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.reon.aggregate.member.command.domain.Authority;
import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.OAuth2;
import com.site.reon.aggregate.member.command.domain.PersonalInfo;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Base64;
import java.util.Collections;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppleOAuth2Token {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String iss;
    private String aud;
    private long exp;
    private long iat;
    private String sub;
    private String nonce;
    @JsonProperty("c_hash")
    private String cHash;
    private String email;
    @JsonProperty("email_verified")
    private String emailVerified;
    @JsonProperty("auth_time")
    private long authTime;
    @JsonProperty("nonce_supported")
    private boolean nonceSupported;
    @JsonProperty("transfer_sub")
    private String transferSub;
    @JsonProperty("real_user_status")
    private String realUserStatus;

    public AppleOAuth2Token(final String token) {
        final String[] chunks = token.split("\\.");
        final Base64.Decoder decoder = Base64.getUrlDecoder();

        final String header = new String(decoder.decode(chunks[0]));
        final String payload = new String(decoder.decode(chunks[1]));

        try {
            final AppleOAuth2Token appleOAuth2Token = objectMapper.readValue(payload, AppleOAuth2Token.class);
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
            this.transferSub = appleOAuth2Token.getTransferSub();
            this.realUserStatus = appleOAuth2Token.getRealUserStatus();
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The token information is invalid.");
        }
    }

    public Member toMember() {
        return Member.builder()
                .email(this.email)
                .password(UUID.randomUUID().toString())
                .personalInfo(PersonalInfo.builder()
                        .firstName(extractEmailName())
                        .lastName(StringUtils.EMPTY)
                        .build())
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .oAuth2(OAuth2.builder()
                        .oAuthClient(OAuth2Client.APPLE)
                        .build())
                .activated(true)
                .build();
    }

    private String extractEmailName() {
        try {
            return this.email.split("@")[0];
        } catch (Exception e) {
            return "User";
        }
    }
}
