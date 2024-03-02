package com.site.reon.aggregate.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AppleOauth2LoginResponse {
    private String state;
    private String code;
    private String idToken;
    private String user;
}
