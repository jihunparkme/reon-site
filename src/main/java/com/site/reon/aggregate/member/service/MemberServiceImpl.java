package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMemberWithAuthorities(String email) {
        return memberRepository.findOneWithAuthoritiesByEmail(email)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberWithAuthorities(String email, OAuth2Client oAuthClient) {
        return memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, oAuthClient)
                .orElse(null);
    }
}
