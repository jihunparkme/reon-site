package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.MemberEditRequest;
import com.site.reon.global.security.exception.NotFoundMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional(readOnly = true)
    public MemberDto getMember(long id) {
        return MemberDto.from(memberRepository.findById(id)
                .orElse(null));
    }

    @Override
    @Transactional
    public void update(MemberEditRequest memberEditRequest, Long id) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isEmpty()) {
            throw new NotFoundMemberException("Not Found Member");
        }

        Member member = memberOpt.get();
        member.update(memberEditRequest);
        memberRepository.save(member);
    }
}
