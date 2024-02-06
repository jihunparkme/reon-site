package com.site.reon.aggregate.member.service.dto.api;

import com.site.reon.global.common.dto.ApiRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ApiEmailVerifyRequest extends ApiRequest {
    @NotBlank(message = "authClientName is required.")
    private String authClientName;

    private String email;

    private String token;
}
