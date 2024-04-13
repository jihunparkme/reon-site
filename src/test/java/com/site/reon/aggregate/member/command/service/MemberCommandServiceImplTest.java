package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.aggregate.member.infra.kakao.service.KakaoOauth2ApiService;
import com.site.reon.aggregate.member.infra.service.MemberEmailAuthCodeService;
import com.site.reon.aggregate.member.query.service.MemberFindService;
import com.site.reon.aggregate.member.query.service.MemberFindServiceImpl;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceImplTest {

    private MemberCommandServiceImpl memberCommandService;
    private MemberFindService memberFindService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private KakaoOauth2ApiService kakaoOauth2ApiService;
    @Mock
    private MemberEmailAuthCodeService memberEmailAuthCodeService;

    @BeforeEach
    void beforeEach() {
        this.memberCommandService =
                new MemberCommandServiceImpl(memberRepository, passwordEncoder, kakaoOauth2ApiService, memberEmailAuthCodeService);
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
                memberCommandService.signUpWithEmail(signUp)
        );
        assertEquals("This email is already registered.", exception.getMessage());
    }

    @Test
    void withdraw_success() throws Exception {
        String email = "user@gmail.com";
        Optional<Member> memberOpt = Optional.of(Member.builder()
                .email(email)
                .build());
        WithdrawRequest request = WithdrawRequest.builder()
                .email(email)
                .authClientName("")
                .build();

        given(memberRepository.findByEmailAndOAuthClient(any(), any()))
                .willReturn(memberOpt);

        boolean result = memberCommandService.withdraw(request);

        Assertions.assertTrue(result);
    }

    @Test
    void withdraw_fail_invalid_client_name() throws Exception {
        String email = "user@gmail.com";
        WithdrawRequest request = WithdrawRequest.builder()
                .email(email)
                .authClientName("XXX")
                .build();


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberCommandService.withdraw(request)
        );

        assertEquals("This is unsupported OAuth2 Client service. Please check authClientName field.", exception.getMessage());
    }

    @Test
    void withdraw_fail_not_exist_member() throws Exception {
        String email = "xxx@gmail.com";
        WithdrawRequest request = WithdrawRequest.builder()
                .email(email)
                .authClientName("google")
                .build();
        given(memberRepository.findByEmailAndOAuthClient(any(), any()))
                .willReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                memberCommandService.withdraw(request)
        );

        assertEquals("No registered member information.", exception.getMessage());
    }
}