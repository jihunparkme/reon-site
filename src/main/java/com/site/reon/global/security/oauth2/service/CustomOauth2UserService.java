package com.site.reon.global.security.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
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

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
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
        Map<String, Object> attributes = getAttributes(registrationId, userRequest, oAuth2User);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.key())),
                attributes,
                userNameAttributeName);
    }

    private Map<String, Object> getAttributes(String registrationId, OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        if (registrationId.contains("apple")) {
            String idToken = userRequest.getAdditionalParameters().get("id_token").toString();
            Map<String, Object> attributes = decodeJwtTokenPayload(idToken);
            attributes.put("id_token", idToken);
        }

        return oAuth2User.getAttributes();
    }

    private void verifyOAuth2LoginServiceSupport(String registrationId) throws OAuth2AuthenticationException {
        if (OAuth2Client.isNotSupport(registrationId)) {
            log.error("unsupported OAuth2 login service. registrationId: {}", registrationId);
            throw new OAuth2AuthenticationException("지원하지 않는 로그인 서비스입니다.");
        }
    }

    public Map<String, Object> decodeJwtTokenPayload(String jwtToken){
        Map<String, Object> jwtClaims = new HashMap<>();
        try {
            String[] parts = jwtToken.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            byte[] decodedBytes = decoder.decode(parts[1].getBytes(StandardCharsets.UTF_8));
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = mapper.readValue(decodedString, Map.class);
            jwtClaims.putAll(map);

        } catch (JsonProcessingException e) {
            log.error("CustomOauth2UserService.decodeJwtTokenPayload exception. jwtToken : {}", jwtToken, e);
        }
        return jwtClaims;
    }
}
