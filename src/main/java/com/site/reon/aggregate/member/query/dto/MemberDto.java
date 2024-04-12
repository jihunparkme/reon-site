package com.site.reon.aggregate.member.query.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.service.dto.AuthorityDto;
import com.site.reon.global.common.constant.member.MemberType;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private long id;
    
    @NotNull
    private MemberType type;

    @NotNull
    @Size(min = 1, max = 30, message = "The first name length must be between 1 and 30 characters.")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30, message = "The last name length must be between 1 and 30 characters.")
    private String lastName;

    @NotNull
    @Size(min = 1, max = 50, message = "The email length must be between 1 and 50 characters.")
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 100, message = "The password length must be between 3 and 100 characters.")
    private String password;

    @NotNull
    @Size(min = 3, max = 20, message = "The phone number length must be between 3 and 20 characters.")
    private String phone;

    @NotNull
    @Size(min = 3, max = 100, message = "The product code length must be between 3 and 100 characters.")
    private String prdCode;

    @NotNull
    @Size(min = 3, max = 100, message = "The roaster S/N length must be between 3 and 100 characters.")
    private String roasterSn;

    private boolean activated;

    private Set<AuthorityDto> authorityDtoSet;

    // COMPANY
    @Size(min = 3, max = 30, message = "The company name length must be between 3 and 30 characters.")
    private String companyName;

    @Size(min = 3, max = 1000, message = "The address length must be between 3 and 1000 characters.")
    private String address;

    private String picture;

    private OAuth2Client oAuthClient;

    public static MemberDto from(Member member) {
        if (member == null) {
            return null;
        }

        return MemberDto.builder()
                .id(member.getId())
                .type(member.getType())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .email(member.getEmail())
                .password(member.getPassword())
                .phone(member.getPhone())
                .companyName(member.getCompanyName())
                .address(member.getAddress())
                .prdCode(member.getPrdCode())
                .roasterSn(member.getRoasterSn())
                .activated(member.isActivated())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder()
                                .authorityName(authority.getAuthorityName())
                                .build())
                        .collect(Collectors.toSet()))
                .picture(member.getPicture())
                .oAuthClient(member.getOAuthClient())
                .build();
    }
}