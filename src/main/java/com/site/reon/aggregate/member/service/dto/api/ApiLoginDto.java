package com.site.reon.aggregate.member.service.dto.api;

import com.site.reon.global.common.annotation.ClientIdConstraint;
import com.site.reon.global.common.annotation.ClientNameConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiLoginDto {
    @ClientIdConstraint
    private String clientId;
    @ClientNameConstraint
    private String clientName;

    @NotBlank(message = "email is required.")
    private String email;

    @NotBlank(message = "password is required.")
    private String password;
}