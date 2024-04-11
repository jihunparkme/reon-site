package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.infra.service.MemberEmailAuthCodeService;
import com.site.reon.aggregate.member.query.service.MemberFindService;
import com.site.reon.aggregate.member.query.service.MemberFindServiceImpl;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberLoginServiceImplTest {

    private MemberLoginService memberLoginService;
    private MemberFindService memberFindService;

    @Mock private MemberRepository memberRepository;

    @Mock private PasswordEncoder passwordEncoder;

    @Mock private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock private MemberEmailAuthCodeService memberEmailAuthCodeService;

    @BeforeEach
    void beforeEach() {
        this.memberLoginService =
                new MemberLoginServiceImpl(memberRepository, passwordEncoder, authenticationManagerBuilder, memberEmailAuthCodeService, null);
        this.memberFindService = new MemberFindServiceImpl(memberRepository);
    }

    @Test
    void signup_is_success() throws Exception {
        String email = "aaron@gmail.com";
        SignUpDto signUp = SignUpDto.builder()
                .email(email)
                .firstName("aaron")
                .lastName("park")
                .password("aaron")
                .build();
        final Member member = Member.builder()
                .firstName(signUp.getFirstName())
                .lastName(signUp.getLastName())
                .email(signUp.getEmail())
                .oAuthClient(OAuth2Client.EMPTY)
                .activated(true)
                .build();

        given(memberRepository.findWithAuthoritiesByEmailAndOAuthClient(any(), any())).willReturn(Optional.of(member));

        Member findMember = memberFindService.getMemberWithAuthorities(email, OAuth2Client.EMPTY);
        assertEquals("aaron", findMember.getFirstName());
        assertEquals("park", findMember.getLastName());
        assertEquals(email, findMember.getEmail());
        assertEquals(OAuth2Client.EMPTY, findMember.getOAuthClient());
        assertEquals(true, findMember.isActivated());
    }

    @Test
    void signup_is_fail() throws Exception {
        String email = "admin@gmail.com";
        SignUpDto signUp = SignUpDto.builder()
                .firstName("admin")
                .lastName("park")
                .email(email)
                .password("admin")
                .build();

        given(memberRepository.save(any())).willThrow(new DuplicateMemberException("This email is already registered."));

        DuplicateMemberException exception = assertThrows(DuplicateMemberException.class, () ->
                memberLoginService.signUpWithEmail(signUp)
        );
        assertEquals("This email is already registered.", exception.getMessage());
    }
}