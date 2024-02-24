package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@Transactional
@SpringBootTest
class MemberLoginServiceImplTest {

    private MemberLoginService memberLoginService;

    @Autowired private MemberRepository memberRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock private MemberAuthCodeService memberAuthCodeService;

    @Autowired private MemberService memberService;

    @BeforeEach
    void beforeEach() {
        this.memberLoginService =
                new MemberLoginServiceImpl(memberRepository, passwordEncoder, authenticationManagerBuilder, memberAuthCodeService);
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

        doNothing().when(memberAuthCodeService).checkEmailVerificationStatus(any(), any());
        memberLoginService.signup(signUp);

        Member member = memberService.getMemberWithAuthorities(email, OAuth2Client.EMPTY);
        assertEquals("aaron", member.getFirstName());
        assertEquals("park", member.getLastName());
        assertEquals(email, member.getEmail());
        assertEquals(OAuth2Client.EMPTY, member.getOAuthClient());
        assertEquals(true, member.isActivated());
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

        doNothing().when(memberAuthCodeService).checkEmailVerificationStatus(any(), any());
        DuplicateMemberException exception = assertThrows(DuplicateMemberException.class, () ->
                memberLoginService.signup(signUp)
        );

        assertEquals("This email is already registered.", exception.getMessage());
    }
}