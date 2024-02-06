package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberService service;

    @Test
    void getMemberWithAuthorities_is_exist_member() throws Exception {
        String email = "admin@gmail.com";

        Member member = service.getMemberWithAuthorities(email, OAuth2Client.EMPTY);

        assertEquals("admin", member.getFirstName());
        assertEquals("park", member.getLastName());
        assertEquals(email, member.getEmail());
    }

    @Test
    void getMemberWithAuthorities_is_not_exist_member() throws Exception {
        String email = "xxx@gmail.com";

        Member member = service.getMemberWithAuthorities(email, OAuth2Client.EMPTY);

        assertEquals(null, member);
    }
}