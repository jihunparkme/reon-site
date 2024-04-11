package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.MemberEditRequest;
import com.site.reon.global.security.exception.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void update(final MemberEditRequest memberEditRequest, final Long id) {
        final Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isEmpty()) {
            throw new NotFoundMemberException();
        }

        final Member member = memberOpt.get();
        member.update(memberEditRequest);
        memberRepository.save(member);
    }
}
