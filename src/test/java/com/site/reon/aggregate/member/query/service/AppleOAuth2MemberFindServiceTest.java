package com.site.reon.aggregate.member.query.service;

import com.site.reon.aggregate.member.command.domain.*;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.query.dto.AppleOAuth2Token;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AppleOAuth2MemberFindServiceTest {
    @Mock
    private MemberRepository memberRepository;

    private AppleOAuth2MemberFindService appleOAuth2MemberFindService;

    @BeforeEach
    void setUp() {
        appleOAuth2MemberFindService = new AppleOAuth2MemberFindService(memberRepository);
    }

    @Test
    void when_getMemberWithAuthorities_then_return_one_member() throws Exception {
        final String email = "admin@gmail.com";
        final Member mockMember = getMockMember(email);
        final AppleOAuth2Token appleOAuth2Token = AppleOAuth2Token.builder()
                .email(email)
                .build();
        given(memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, OAuth2Client.APPLE))
                .willReturn(Optional.of(mockMember));

        final Member member = appleOAuth2MemberFindService.getMemberInfo(appleOAuth2Token);

        assertEquals("aaron", member.getPersonalInfo().getFirstName());
        assertEquals("park", member.getPersonalInfo().getLastName());
        assertEquals(OAuth2Client.APPLE, member.getOAuth2().getOAuthClient());
        assertEquals(email, member.getEmail());
    }

    private Member getMockMember(final String email) {
        return Member.builder()
                .id(1L)
                .email(email)
                .personalInfo(PersonalInfo.builder()
                        .firstName("aaron")
                        .lastName("park")
                        .build())
                .productInfo(new ProductInfo())
                .oAuth2(OAuth2.builder()
                        .oAuthClient(OAuth2Client.APPLE)
                        .build())
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .build();
    }
}