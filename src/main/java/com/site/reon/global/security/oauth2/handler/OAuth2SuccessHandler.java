package com.site.reon.global.security.oauth2.handler;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.domain.repository.MemberRepository;
import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.security.dto.SessionMember;
import com.site.reon.global.security.oauth2.dto.OAuthAttributes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final HttpSession httpSession;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, attributes); // 서비스 마다 다른 키와 벨류 값을 변환하여 객체 생성
        Member member = getMemberInfo(oAuthAttributes);

        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, SessionMember.from(member));
        response.sendRedirect("/");
    }

    private Member getMemberInfo(OAuthAttributes oAuthAttributes) {
        Optional<Member> memberOpt = memberRepository.findWithAuthoritiesByEmailAndOAuthClient(
                oAuthAttributes.getEmail(), oAuthAttributes.getOAuthClient());
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.oAuth2UserUpdate(oAuthAttributes.getName(), oAuthAttributes.getPicture());
            memberRepository.save(member);
            return member;
        }

        Member member = oAuthAttributes.toMember();
        memberRepository.save(member);
        return member;
    }
}
