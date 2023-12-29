package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.MemberDto;
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
class MemberServiceImplTest {

    @Autowired
    private MemberService service;

    @Test
    void signup_is_success() throws Exception {
        String email = "aaron@gmail.com";
        SignUpDto signUp = SignUpDto.builder()
                .firstName("aaron")
                .lastName("park")
                .email(email)
                .password("aaron")
                .build();

        service.signup(signUp);

        MemberDto member = service.getMemberWithAuthorities(email);
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
                service.signup(signUp)
        );

        assertEquals("이미 가입되어 있는 이메일입니다.", exception.getMessage());
    }

    @Test
    void getMemberWithAuthorities_is_exist_member() throws Exception {
        String email = "admin@gmail.com";

        MemberDto member = service.getMemberWithAuthorities(email);

        assertEquals("admin", member.getFirstName());
        assertEquals("park", member.getLastName());
        assertEquals(email, member.getEmail());
    }

    @Test
    void getMemberWithAuthorities_is_not_exist_member() throws Exception {
        String email = "xxx@gmail.com";

        MemberDto member = service.getMemberWithAuthorities(email);

        assertEquals(null, member);
    }
}