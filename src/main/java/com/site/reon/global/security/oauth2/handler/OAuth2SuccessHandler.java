package com.site.reon.global.security.oauth2.handler;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.common.event.Events;
import com.site.reon.global.common.util.infra.RedisUtilService;
import com.site.reon.global.event.dto.GoogleOauth2TokenRevokeEvent;
import com.site.reon.global.security.dto.SessionMember;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import com.site.reon.global.security.oauth2.dto.OAuthAttributes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final HttpSession httpSession;
    private final MemberRepository memberRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final RedisUtilService redisUtilService;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        final Map<String, Object> attributes = oAuth2User.getAttributes();

        final OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        final String registrationId = authenticationToken.getAuthorizedClientRegistrationId();

        final String signalKey = hasWithdrawSignal(registrationId, attributes);
        if (StringUtils.isNotEmpty(signalKey)) {
            final OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    authenticationToken.getAuthorizedClientRegistrationId(),
                    authenticationToken.getName());
            final String accessToken = authorizedClient.getAccessToken().getTokenValue();

            log.info("[Event] send google oauth2 revoke Event.");
            Events.raise(GoogleOauth2TokenRevokeEvent.builder()
                    .accessToken(accessToken)
                    .signalKey(signalKey)
                    .email((String) attributes.get("email"))
                    .build());

            response.sendRedirect("/logout");
            return;
        }

        final OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, attributes); // 서비스 마다 다른 키와 벨류 값을 변환하여 객체 생성
        final Member member = getMemberInfo(oAuthAttributes);

        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, SessionMember.from(member));
        response.sendRedirect("/");
    }

    private String hasWithdrawSignal(final String registrationId, final Map<String, Object> attributes) {
        if (OAuth2Client.isGoogleLogin(registrationId)) {
            final String email = (String) attributes.get("email");
            final String signalKey = KeyPrefix.WITHDRAW.prefix() + KeyPrefix.WITHDRAW.verifyPrefix() + ":" + email;
            final Optional<String> withdrawSignalOpt = redisUtilService.getValueOf(signalKey);
            if (withdrawSignalOpt.isPresent()) {
                return signalKey;
            }
        }

        return StringUtils.EMPTY;
    }

    private Member getMemberInfo(OAuthAttributes oAuthAttributes) {
        final Optional<Member> memberOpt = memberRepository.findWithAuthoritiesByEmailAndOAuthClient(
                oAuthAttributes.getEmail(), oAuthAttributes.getOAuthClient());
        if (memberOpt.isPresent()) {
            final Member member = memberOpt.get();
            member.oAuth2UserUpdate(oAuthAttributes.getName(), oAuthAttributes.getPicture());
            memberRepository.save(member);
            return member;
        }

        final Member member = oAuthAttributes.toMember();
        memberRepository.save(member);
        return member;
    }
}
