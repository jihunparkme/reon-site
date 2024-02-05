package com.site.reon.aggregate.member.service.dto.api;

import com.site.reon.global.common.dto.ApiRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ApiLoginRequest extends ApiRequest {
    @NotBlank(message = "email is required.")
    private String email;

    @NotBlank(message = "password is required.")
    private String password;
}