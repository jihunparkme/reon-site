package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.WithdrawRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MemberCommandServiceImplTest {

    private MemberRepository memberRepository = mock(MemberRepository.class);
    private MemberCommandServiceImpl memberCommandService = new MemberCommandServiceImpl(memberRepository, null);

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
        given(memberRepository.deleteByEmailAndOAuthClient(any(), any()))
                .willReturn(1);

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