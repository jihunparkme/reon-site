package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.domain.Authority;
import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.OAuth2;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.command.dto.ApiOAuth2SignUpRequest;
import com.site.reon.aggregate.member.query.dto.MemberDto;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MemberCommandApiServiceImplTest {

    private MemberRepository memberRepository = mock(MemberRepository.class);
    final MemberCommandApiService memberCommandApiService = new MemberCommandApiServiceImpl(memberRepository);

    @Test
    void oAuth2SignUp_success() throws Exception {
        String email = "user@gmail.com";
        ApiOAuth2SignUpRequest apiOAuth2SignUp = ApiOAuth2SignUpRequest.builder()
                .roasterSn("asfdasfeasfdsasdfas")
                .email(email)
                .firstName("aaron")
                .picture("safddsafdsafs")
                .authClientName("kakao")
                .build();
        final OAuth2 oAuth2 = OAuth2.builder()
                .picture("safddsafdsafs")
                .oAuthClient(OAuth2Client.KAKAO)
                .build();
        final Member expected = Member.builder()
                .id(3L)
                .roasterSn("asfdasfeasfdsasdfas")
                .email(email)
                .firstName("aaron")
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .oAuth2(oAuth2)
                .build();

        given(memberRepository.save(any()))
                .willReturn(expected);

        MemberDto member = memberCommandApiService.oAuth2SignUp(apiOAuth2SignUp);
        Assertions.assertEquals("asfdasfeasfdsasdfas", member.getRoasterSn());
        Assertions.assertEquals("aaron", member.getFirstName());
        Assertions.assertEquals("safddsafdsafs", member.getPicture());
        Assertions.assertEquals(OAuth2Client.KAKAO, member.getOAuthClient());
    }

    @Test
    void oAuth2SignUp_fail() throws Exception {
        String email = "user@gmail.com";
        ApiOAuth2SignUpRequest apiOAuth2SignUp = ApiOAuth2SignUpRequest.builder()
                .roasterSn("asfdasfeasfdsasdfas")
                .email(email)
                .firstName("aaron")
                .picture("safddsafdsafs")
                .authClientName("aaaa")
                .build();


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberCommandApiService.oAuth2SignUp(apiOAuth2SignUp)
        );

        assertEquals("This is unsupported OAuth2 Client service. Please check authClientName field.", exception.getMessage());
    }
}