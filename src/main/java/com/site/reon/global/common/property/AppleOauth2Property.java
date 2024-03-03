package com.site.reon.global.common.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("oauth2.client.apple")
public class AppleOauth2Property {
    private String teamId;
    private String clientId;
    private String keyId;
    private String redirectUri;
    private String responseMode;
    private String nonce;
    private String authorizationUri;
    private String responseType;
    private String scope;
    private String clientName;
}