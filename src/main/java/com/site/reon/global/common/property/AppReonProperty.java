package com.site.reon.global.common.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.reon")
public class AppReonProperty {
    private String clientName;
    private String clientId;
}
