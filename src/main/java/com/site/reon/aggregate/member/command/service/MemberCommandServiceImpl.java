package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.infra.kakao.dto.KakaoOauth2UnlinkResponse;
import com.site.reon.aggregate.member.infra.kakao.service.KakaoOauth2ApiService;
import com.site.reon.aggregate.member.command.dto.MemberEditRequest;
import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.aggregate.member.command.dto.ApiRegisterMemberSerialNo;
import com.site.reon.global.security.exception.NotFoundMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    private final KakaoOauth2ApiService kakaoOauth2ApiService;

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

    @Override
    @Transactional
    public boolean registerSerialNo(final long memberId, final ApiRegisterMemberSerialNo request) {
        int result = memberRepository.registerMemberSerialNo(request.getSerialNo(), memberId);
        return result == 1;
    }

    @Override
    @Transactional
    public boolean withdraw(final WithdrawRequest request) {
        final String email = request.getEmail();
        final String authClientName = request.getAuthClientName().toLowerCase();
        if (isEmailMember(authClientName)) {
            deleteMember(email, OAuth2Client.EMPTY);
            return true;
        }

        OAuth2Client.validateClientName(authClientName);
        deleteMember(email, OAuth2Client.of(authClientName));
        return true;
    }

    private void deleteMember(final String email, final OAuth2Client oAuth2Client) {
        final Optional<Member> memberOpt = memberRepository.findByEmailAndOAuthClient(email, oAuth2Client);
        if (memberOpt.isEmpty()) {
            throw new IllegalArgumentException("No registered member information.");
        }

        final Member member = memberOpt.get();
        memberRepository.delete(member);

        if (oAuth2Client.isKakaoLogin()) {
            requestUnlink(member);
        }
    }

    private void requestUnlink(final Member member) {
        final Long oauthUserId = member.getOauthUserId();
        if (oauthUserId == null) {
            return;
        }

        final KakaoOauth2UnlinkResponse response = kakaoOauth2ApiService.unlink(oauthUserId);
        if (StringUtils.isNotEmpty(response.msg())) {
            log.error("{} unlink api call error. msg: {}, code: {}", member.getOAuthClient(), response.msg(), response.code());
        }
    }

    private boolean isEmailMember(String authClientName) {
        return StringUtils.isEmpty(authClientName) || "empty".equals(authClientName);
    }
}
