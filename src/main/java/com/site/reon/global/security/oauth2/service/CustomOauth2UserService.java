package com.site.reon.global.security.oauth2.service;

import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.oauth2.dto.SocialLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // 소셜 인증 후 가져온 유저 정보

        // Oauth2 Login Service identification ID (kakao, naver, google..)
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        verifyOAuth2LoginServiceSupport(registrationId);

        // OAuth2 login key (primary Key). only google
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2User attribute info
        Map<String, Object> attributes = oAuth2User.getAttributes();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.key())),
                attributes,
                userNameAttributeName);
    }

    private void verifyOAuth2LoginServiceSupport(String registrationId) throws OAuth2AuthenticationException {
        if (SocialLogin.isNotSupport(registrationId)) {
            log.error("unsupported OAuth2 login service. registrationId: {}", registrationId);
            throw new OAuth2AuthenticationException("지원하지 않는 로그인 서비스입니다.");
        }
    }
}
