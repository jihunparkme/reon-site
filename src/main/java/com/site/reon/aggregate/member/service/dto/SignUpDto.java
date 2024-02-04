package com.site.reon.aggregate.member.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @NotBlank(message = "email is required.")
    @Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}", message = "이메일 형식을 확인해 주세요.")
    @Size(min = 3, max = 50)
    private String email;

    @NotBlank(message = "firstName is required.")
    @Size(min = 1, max = 30)
    private String firstName;

    @NotBlank(message = "lastName is required.")
    @Size(min = 1, max = 30)
    private String lastName;

    @NotBlank(message = "password is required.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,50}$", message = "비밀번호는 최소 8자에서 50자까지, 영문자, 숫자 및 특수 문자를 포함해야 합니다.")
    private String password;

    private String roasterSn;
}