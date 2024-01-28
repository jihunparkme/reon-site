package com.site.reon.aggregate.member.service.dto;

import com.site.reon.global.common.annotation.ClientIdConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiEmailVerifyDto {
    @ClientIdConstraint
    private String clientId;

    private String clientName;
    private String email;
    private String token;
}
