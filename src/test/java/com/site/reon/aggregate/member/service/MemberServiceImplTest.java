package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberServiceImpl(memberRepository);
    }

    @Test
    void getMemberWithAuthorities_is_exist_member() throws Exception {
        String email = "admin@gmail.com";
        final Member willReturn = Member.builder()
                .firstName("admin")
                .lastName("park")
                .email(email)
                .build();
        given(memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, OAuth2Client.EMPTY))
                .willReturn(Optional.of(willReturn));

        Member member = memberService.getMemberWithAuthorities(email, OAuth2Client.EMPTY);

        assertEquals("admin", member.getFirstName());
        assertEquals("park", member.getLastName());
        assertEquals(email, member.getEmail());
    }

    @Test
    void getMemberWithAuthorities_is_not_exist_member() throws Exception {
        String email = "xxx@gmail.com";
        given(memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, OAuth2Client.EMPTY))
                .willReturn(Optional.empty());

        Member member = memberService.getMemberWithAuthorities(email, OAuth2Client.EMPTY);

        assertEquals(null, member);
    }
}