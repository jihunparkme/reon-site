package com.site.reon.aggregate.member.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthCodeVerifyRequest {
    @NotBlank(message = "email is required.")
    private String email;

    @NotBlank(message = "authCode is required.")
    private String authCode;
}
