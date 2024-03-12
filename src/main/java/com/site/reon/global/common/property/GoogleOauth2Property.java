package com.site.reon.global.common.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("oauth2.client.google")
public class GoogleOauth2Property {
    private String clientId;
    private String clientSecret;
    private String clientName;
    private String scope;
    private String responseType;
    private String authorizationUri;
    private String redirectUri;
}