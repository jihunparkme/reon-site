package com.site.reon.aggregate.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerifyDto {
    private String clientName;
    private String email;
    private String token;
}
