package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.command.domain.*;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.query.dto.MemberDto;
import com.site.reon.aggregate.member.query.service.MemberFindService;
import com.site.reon.aggregate.member.query.service.MemberFindServiceImpl;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberFindServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberFindService memberFindService;

    @BeforeEach
    void setUp() {
        memberFindService = new MemberFindServiceImpl(memberRepository);
    }

    @Test
    void when_getMemberWithAuthorities_then_return_two_member() {
        final String email = "admin@gmail.com";
        final Member mockMember1 = getMockMember(email);
        final Member mockMember2 = getMockOAuth2Member(email);
        given(memberRepository.findOneWithAuthoritiesByEmail(email))
                .willReturn(Optional.of(List.of(mockMember1, mockMember2)));

        final List<Member> members = memberFindService.getMemberWithAuthorities(email);

        assertEquals(2, members.size());
        assertEquals("admin", members.get(0).getPersonalInfo().getFirstName());
        assertEquals("admin", members.get(1).getPersonalInfo().getFirstName());
        assertEquals(OAuth2Client.EMPTY, members.get(0).getOAuth2().getOAuthClient());
        assertEquals(OAuth2Client.KAKAO, members.get(1).getOAuth2().getOAuthClient());
    }

    @Test
    void when_getMemberWithAuthorities_then_return_empty_list() {
        final String email = "admin@gmail.com";
        given(memberRepository.findOneWithAuthoritiesByEmail(email))
                .willReturn(Optional.of(Collections.EMPTY_LIST));

        final List<Member> members = memberFindService.getMemberWithAuthorities(email);

        assertEquals(0, members.size());
    }

    @Test
    void when_getMemberWithAuthorities_then_return_one_member() throws Exception {
        String email = "admin@gmail.com";
        final Member mockMember = getMockMember(email);
        given(memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, OAuth2Client.EMPTY))
                .willReturn(Optional.of(mockMember));

        Member member = memberFindService.getMemberWithAuthorities(email, OAuth2Client.EMPTY);

        assertEquals("admin", member.getPersonalInfo().getFirstName());
        assertEquals("park", member.getPersonalInfo().getLastName());
        assertEquals(email, member.getEmail());
    }

    @Test
    void when_getMemberWithAuthorities_then_return_null() throws Exception {
        String email = "xxx@gmail.com";
        given(memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, OAuth2Client.EMPTY))
                .willReturn(Optional.empty());

        Member member = memberFindService.getMemberWithAuthorities(email, OAuth2Client.EMPTY);

        assertEquals(null, member);
    }

    @Test
    void when_getMember_then_return_one_member() throws Exception {
        String email = "admin@gmail.com";
        final long memberId = 1L;
        final Member mockMember = getMockMember(email);
        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(mockMember));

        final MemberDto member = memberFindService.getMember(memberId);

        assertEquals("admin", member.getFirstName());
        assertEquals("park", member.getLastName());
        assertEquals(email, member.getEmail());
    }

    @Test
    void when_getMember_then_return_null() throws Exception {
        final long memberId = 1L;
        given(memberRepository.findById(memberId))
                .willReturn(Optional.empty());

        final MemberDto member = memberFindService.getMember(memberId);

        assertEquals(null, member);
    }

    private Member getMockMember(final String email) {
        return Member.builder()
                .id(1L)
                .email(email)
                .personalInfo(PersonalInfo.builder()
                        .firstName(email.split("@")[0])
                        .lastName("park")
                        .build())
                .productInfo(new ProductInfo())
                .oAuth2(OAuth2.builder()
                        .oAuthClient(OAuth2Client.EMPTY)
                        .build())
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .build();
    }

    private Member getMockOAuth2Member(final String email) {
        return Member.builder()
                .id(2L)
                .email(email)
                .personalInfo(PersonalInfo.builder()
                        .firstName(email.split("@")[0])
                        .lastName("park")
                        .build())
                .productInfo(new ProductInfo())
                .oAuth2(OAuth2.builder()
                        .oAuthClient(OAuth2Client.KAKAO)
                        .build())
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .build();
    }
}