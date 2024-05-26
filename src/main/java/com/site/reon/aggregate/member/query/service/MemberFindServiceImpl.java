package com.site.reon.aggregate.member.query.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.query.dto.MemberSearchRequestParam;
import com.site.reon.aggregate.member.query.dto.MemberDto;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFindServiceImpl implements MemberFindService {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> getMemberWithAuthorities(final String email) {
        return memberRepository.findOneWithAuthoritiesByEmail(email)
                .orElse(null);
    }

    @Override
    public Member getMemberWithAuthorities(final String email, final OAuth2Client oAuthClient) {
        return memberRepository.findWithAuthoritiesByEmailAndOAuthClient(email, oAuthClient)
                .orElse(null);
    }

    @Override
    public MemberDto getMember(final long id) {
        return MemberDto.from(memberRepository.findById(id)
                .orElse(null));
    }

    @Override
    public Page<Member> findAll(final MemberSearchRequestParam param) {
        final var pageable = PageRequest.of(param.getPage(), param.getSize(), Sort.by("id").descending());
        return memberRepository.findAll(pageable);
    }

    @Override
    public Page<Member> findAllByFilter(final MemberSearchRequestParam param) {
        return memberRepository.findAllByFilter(param);
    }
}
