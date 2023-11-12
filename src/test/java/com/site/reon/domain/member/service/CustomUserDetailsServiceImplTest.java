package com.site.reon.domain.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomUserDetailsServiceImplTest {

    @Autowired
    UserDetailsService service;

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
}