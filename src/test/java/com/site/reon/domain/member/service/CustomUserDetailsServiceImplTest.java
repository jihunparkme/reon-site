package com.site.reon.domain.member.service;

import com.site.reon.global.common.constant.member.Role;
import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.repository.MemberRepository;
import com.site.reon.aggregate.member.service.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomUserDetailsServiceImplTest {

    @Autowired
    private MemberRepository memberRepository;

    CustomUserDetailsServiceImpl service;

    @BeforeEach
    void beforeEach() {
        service = new CustomUserDetailsServiceImpl(memberRepository);
    }

    @Test
    void loadUserByUsername() throws Exception {
        UserDetails user = service.loadUserByUsername("user@gmail.com");

        assertEquals("user", user.getUsername());
    }

    @Test
    void loadUserByUsername_should_return_exception() throws Exception {

        String email = "xxx@gmail.com";
        assertThatThrownBy(() -> {
            service.loadUserByUsername(email);
        }).isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("회원 정보를 찾을 수 없습니다.");

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                service.loadUserByUsername(email)
        );
        assertEquals(email + " : 회원 정보를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    void createUser_is_not_active_member() throws Exception {
        String email = "xxx@gmail.com";
        Member member = Member.builder()
                .activated(false)
                .build();

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.createUser(email, member)
        );
        assertEquals(email + " : 비활성 계정입니다.", exception.getMessage());
    }

    @Test
    void createUser_is_active_member() throws Exception {
        String email = "aaron@gmail.com";
        Authority authority = Authority.builder()
                .authorityName(Role.USER.key())
                .build();
        Member member = Member.builder()
                .activated(true)
                .authorities(new HashSet(Arrays.asList(authority)))
                .email(email)
                .password("aaron")
                .build();

        User user = service.createUser(email, member);

        assertEquals("aaron", user.getUsername());
    }
}