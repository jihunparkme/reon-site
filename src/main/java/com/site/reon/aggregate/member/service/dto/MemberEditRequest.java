package com.site.reon.aggregate.member.service.dto;

import com.site.reon.global.common.constant.member.MemberType;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditRequest {

    private OAuth2Client oAuthClient;
    private String email;

    private MemberType type;

    @NotNull(message = "firstName is required.")
    @Size(min = 1, max = 30, message = "The first name length must be between 1 and 30 characters.")
    private String firstName;

    @Size(max = 30, message = "The last name length must not exceed 30 characters.")
    private String lastName;

    @Size(max = 20, message = "The phone number length must not exceed 20 characters.")
    private String phone;

    @Size(max = 100, message = "The product code length must not exceed 100 characters.")
    private String prdCode;

    @Size(max = 100, message = "The roaster S/N length must not exceed 100 characters.")
    private String roasterSn;

    @Size(max = 30, message = "The company name length must not exceed 30 characters.")
    private String companyName;

    @Size(max = 1000, message = "The address length must not exceed 1000 characters.")
    private String address;
}