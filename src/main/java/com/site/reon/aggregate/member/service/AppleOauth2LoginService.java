package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.AppleOAuth2Token;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppleOauth2LoginService {
    private final MemberRepository memberRepository;

    public Member getMemberInfo(final AppleOAuth2Token appleOAuth2Token) {
        Optional<Member> memberOpt = memberRepository.findWithAuthoritiesByEmailAndOAuthClient(
                appleOAuth2Token.getEmail(), OAuth2Client.APPLE);
        if (memberOpt.isPresent()) {
            return memberOpt.get();
        }

        Member member = appleOAuth2Token.toMember();
        memberRepository.save(member);
        return member;
    }
}
