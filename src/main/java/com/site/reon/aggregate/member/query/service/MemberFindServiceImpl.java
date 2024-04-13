package com.site.reon.aggregate.member.query.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.query.dto.MemberDto;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberFindServiceImpl implements MemberFindService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMemberWithAuthorities(final String email) {
        return memberRepository.findOneWithAuthoritiesByEmail(email)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberWithAuthorities(final String email, final OAuth2Client oAuthClient) {
        return memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, oAuthClient)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto getMember(final long id) {
        return MemberDto.from(memberRepository.findById(id)
                .orElse(null));
    }
}
