package com.site.reon.aggregate.index.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMailRequest {
    @NotBlank(message = "name is required.")
    private String name;

    @NotBlank(message = "email is required.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}", message = "Please check the email format.")
    @Size(min = 3, max = 50, message = "The email length must be between 3 and 50 characters.")
    private String email;

    @NotBlank(message = "subject is required.")
    private String subject;

    @NotBlank(message = "message is required.")
    private String message;
}
