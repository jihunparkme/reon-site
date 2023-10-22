package com.site.reon.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.site.reon.domain.member.constant.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @NotNull
    private UserType type;

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

    @Size(min = 3, max = 30)
    private String companyName;

    @Size(min = 3, max = 1000)
    private String address;

    @NotNull
    @Size(min = 3, max = 100)
    private String prdCode;

    @NotNull
    @Size(min = 3, max = 100)
    private String roasterSn;

//    private Set<AuthorityDto> authorityDtoSet;
//
//    public static MemberDto from(Member member) {
//        if(member == null) return null;
//
//        return MemberDto.builder()
//                .username(member.getUsername())
//                .nickname(member.getNickname())
//                .authorityDtoSet(member.getAuthorities().stream()
//                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
//                        .collect(Collectors.toSet()))
//                .build();
//    }
}