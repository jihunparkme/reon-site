package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.PersonalInfo;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.query.service.MemberFindService;
import com.site.reon.aggregate.member.query.service.MemberFindServiceImpl;
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
class MemberFindServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    private MemberFindService memberFindService;

    @BeforeEach
    void setUp() {
        memberFindService = new MemberFindServiceImpl(memberRepository);
    }

    @Test
    void getMemberWithAuthorities_is_exist_member() throws Exception {
        String email = "admin@gmail.com";
        final Member willReturn = Member.builder()
                .email(email)
                .personalInfo(PersonalInfo.builder()
                        .firstName("admin")
                        .lastName("park")
                        .build())
                .build();
        given(memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, OAuth2Client.EMPTY))
                .willReturn(Optional.of(willReturn));

        Member member = memberFindService.getMemberWithAuthorities(email, OAuth2Client.EMPTY);

        assertEquals("admin", member.getPersonalInfo().getFirstName());
        assertEquals("park", member.getPersonalInfo().getLastName());
        assertEquals(email, member.getEmail());
    }

    @Test
    void getMemberWithAuthorities_is_not_exist_member() throws Exception {
        String email = "xxx@gmail.com";
        given(memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, OAuth2Client.EMPTY))
                .willReturn(Optional.empty());

        Member member = memberFindService.getMemberWithAuthorities(email, OAuth2Client.EMPTY);

        assertEquals(null, member);
    }
}