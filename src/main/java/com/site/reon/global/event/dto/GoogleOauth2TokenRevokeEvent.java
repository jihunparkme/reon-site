package com.site.reon.global.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleOauth2TokenRevokeEvent {
    private String accessToken;
    private String signalKey;
    private String email;
}
