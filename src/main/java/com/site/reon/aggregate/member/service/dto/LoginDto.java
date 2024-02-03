package com.site.reon.aggregate.member.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message = "email is required.")
    private String email;

    @NotBlank(message = "password is required.")
    private String password;

    public static LoginDto from(ApiLoginDto apiLoginDto) {
        return LoginDto.builder()
                .email(apiLoginDto.getEmail())
                .password(apiLoginDto.getPassword())
                .build();
    }
}