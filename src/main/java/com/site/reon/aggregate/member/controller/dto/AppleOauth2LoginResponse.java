package com.site.reon.aggregate.member.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleOauth2LoginResponse {
    private String state;
    private String code;
    private String id_token;
    private String user;
}
