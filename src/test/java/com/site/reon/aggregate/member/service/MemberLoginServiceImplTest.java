package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.security.exception.DuplicateMemberException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberLoginServiceImplTest {

    @Autowired
    private MemberLoginService memberLoginService;

    @Autowired
    private MemberService memberService;

    @Test
    void signup_is_success() throws Exception {
        String email = "aaron@gmail.com";
        SignUpDto signUp = SignUpDto.builder()
                .firstName("aaron")
                .lastName("park")
                .email(email)
                .password("aaron")
                .build();

        memberLoginService.signup(signUp);

        Member member = memberService.getMemberWithAuthorities(email);
        assertEquals("aaron", member.getFirstName());
        assertEquals("park", member.getLastName());
        assertEquals(email, member.getEmail());
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

        DuplicateMemberException exception = assertThrows(DuplicateMemberException.class, () ->
                memberLoginService.signup(signUp)
        );

        assertEquals("이미 가입되어 있는 이메일입니다.", exception.getMessage());
    }
}