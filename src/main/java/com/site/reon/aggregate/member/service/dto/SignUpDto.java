package com.site.reon.aggregate.member.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @NotBlank(message = "email is required.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}", message = "Please check the email format.")
    @Size(min = 3, max = 50)
    private String email;

    @NotBlank(message = "firstName is required.")
    @Size(min = 1, max = 30)
    private String firstName;

    @NotBlank(message = "lastName is required.")
    @Size(min = 1, max = 30)
    private String lastName;

    @NotBlank(message = "password is required.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,50}$", message = "The password must be between 8 and 50 characters long and include letters, numbers, and special characters.")
    private String password;

    private String roasterSn;
}