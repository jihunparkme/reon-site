package com.site.reon.aggregate.member.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthCodeRequest {
    @NotBlank(message = "email is required.")
    private String email;
}
