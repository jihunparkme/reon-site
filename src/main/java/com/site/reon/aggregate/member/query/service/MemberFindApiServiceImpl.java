package com.site.reon.aggregate.member.query.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.domain.repository.MemberRepository;
import com.site.reon.aggregate.member.service.dto.AppleOAuth2Token;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberFindApiServiceImpl implements MemberFindApiService {

    private final MemberRepository memberRepository;

    @Override
    public boolean verifySocialEmail(final ApiEmailVerifyRequest request) {
        final String authClientName = request.getAuthClientName().toLowerCase();
        OAuth2Client.validateClientName(authClientName);

        final OAuth2Client oAuth2Client = OAuth2Client.of(authClientName);
        if (OAuth2Client.APPLE == oAuth2Client) {
            return verifyAppleEmail(request.getToken(), oAuth2Client);
        }

        return verifyKakaoAndGoogleEmail(request.getEmail(), oAuth2Client);
    }

    private boolean verifyAppleEmail(final String token, final OAuth2Client oAuth2Client) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Token is required for Apple login.");
        }

        final AppleOAuth2Token appleOAuth2Token = new AppleOAuth2Token(token);
        final String email = appleOAuth2Token.getEmail();
        if (StringUtils.isEmpty(email)) {
            log.warn("The token information is invalid. token: {}", token);
            throw new IllegalArgumentException("The token information is invalid.");
        }

        return isExistMember(email, oAuth2Client);
    }

    private boolean verifyKakaoAndGoogleEmail(final String email, final OAuth2Client oAuth2Client) {
        if (StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException("Email is required for Kakao, Google login.");
        }

        return isExistMember(email, oAuth2Client);
    }

    private boolean isExistMember(final String email, final OAuth2Client oAuthClient) {
        final Optional<Member> memberOpt = memberRepository.findByEmailAndOAuthClient(email, oAuthClient);
        if (memberOpt.isPresent()) {
            return true;
        }

        return false;
    }
}
