package com.site.reon.aggregate.member.command.dto;

import com.site.reon.aggregate.member.controller.dto.ApiLoginRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "email is required.")
    private String email;

    @NotBlank(message = "password is required.")
    private String password;

    public static LoginRequest from(ApiLoginRequest request) {
        return LoginRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}