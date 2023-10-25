package com.site.reon.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.site.reon.domain.member.constant.MemberType;
import com.site.reon.domain.member.entity.Member;
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

    @NotNull
    private MemberType type;

    @NotNull
    @Size(min = 1, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30)
    private String lastName;

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 20)
    private String phone;

    @NotNull
    @Size(min = 3, max = 100)
    private String prdCode;

    @NotNull
    @Size(min = 3, max = 100)
    private String roasterSn;

    private boolean activated;

    private Set<AuthorityDto> authorityDtoSet;

    // COMPANY
    @Size(min = 3, max = 30)
    private String companyName;

    @Size(min = 3, max = 1000)
    private String address;

    public static MemberDto from(Member member) {
        if (member == null) {
            return null;
        }

        return MemberDto.builder()
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
                .build();
    }
}