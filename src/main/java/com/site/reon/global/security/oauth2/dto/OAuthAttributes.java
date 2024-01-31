package com.site.reon.global.security.oauth2.dto;

import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.global.common.constant.member.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;

/**
 * OAuth2UserService 를 통해 가져온 OAuth2User attribute 를 담는 클래스
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private OAuth2Client oAuth2Client;

    /**
     * OAtuh2User attributes 정보를 OAuthAttributes 클래스로 변환
     */
    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        if (OAuth2Client.isKakaoLogin(registrationId)) {
            return ofKakao(OAuth2Client.KAKAO.getNameAttributeName(), attributes);
        }

        return ofGoogle(OAuth2Client.GOOGLE.getNameAttributeName(), attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .oAuth2Client(OAuth2Client.KAKAO)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .oAuth2Client(OAuth2Client.GOOGLE)
                .build();
    }

    public Member toMember() {
        return Member.builder()
                .firstName(this.name)
                .lastName(StringUtils.EMPTY)
                .email(this.email)
                .password(StringUtils.EMPTY)
                .picture(this.picture)
                .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
                .oAuth2Client(this.oAuth2Client)
                .activated(true)
                .build();
    }
}
