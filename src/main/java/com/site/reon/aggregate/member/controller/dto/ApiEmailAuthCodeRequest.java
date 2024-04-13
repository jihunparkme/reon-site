package com.site.reon.aggregate.member.controller.dto;

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
public class ApiEmailAuthCodeRequest extends ApiRequest {
    @NotBlank(message = "purpose is required.")
    private String purpose;

    @NotBlank(message = "email is required.")
    private String email;
}
