package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.command.dto.ApiOAuth2SignUpRequest;
import com.site.reon.aggregate.member.query.dto.MemberDto;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandApiServiceImpl implements MemberCommandApiService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberDto oAuth2SignUp(final ApiOAuth2SignUpRequest request) {
        String authClientName = request.getAuthClientName().toLowerCase();
        OAuth2Client.validateClientName(authClientName);
        validateEmailAndOAuthClient(request.getEmail(), OAuth2Client.of(authClientName));

        Member member = request.toMember();
        return MemberDto.from(memberRepository.save(member));
    }

    private void validateEmailAndOAuthClient(final String email, final OAuth2Client oAuthClient) {
        final Optional<Member> memberOpt = memberRepository.findByEmailAndOAuthClient(email, oAuthClient);
        if (memberOpt.isPresent()) {
            throw new DuplicateMemberException();
        }
    }
}
