package com.site.reon.aggregate.member.service.dto;

import com.site.reon.global.common.annotation.ClientIdConstraint;
import com.site.reon.global.common.annotation.ClientNameConstraint;
import jakarta.validation.constraints.NotBlank;
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
    @ClientNameConstraint
    private String clientName;

    @NotBlank(message = "authClientName is required.")
    private String authClientName;
    private String email;
    private String token;
}
