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
    @Size(min = 1, max = 30, message = "firstName size must be between 1 and 30.")
    private String firstName;

    @Size(max = 30)
    private String lastName;

    @Size(max = 20)
    private String phone;

    @Size(max = 100)
    private String prdCode;

    @Size(max = 100)
    private String roasterSn;

    @Size(max = 30)
    private String companyName;

    @Size(max = 1000)
    private String address;
}