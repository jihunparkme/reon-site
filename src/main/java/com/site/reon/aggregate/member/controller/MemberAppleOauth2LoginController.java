package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.AppleOauth2LoginService;
import com.site.reon.aggregate.member.service.dto.AppleOAuth2Token;
import com.site.reon.aggregate.member.service.dto.AppleOauth2LoginResponse;
import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.common.property.AppleOauth2Property;
import com.site.reon.global.security.dto.SessionMember;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class MemberAppleOauth2LoginController {

    private final HttpSession httpSession;
    private final AppleOauth2Property appleOauth2Property;
    private final AppleOauth2LoginService appleOauth2LoginService;

    @GetMapping(value = "/authorization/apple")
    public String authorization(RedirectAttributes attr) {
        attr.addAttribute("client_id", appleOauth2Property.getClientId());
        attr.addAttribute("redirect_uri", appleOauth2Property.getRedirectUri());
        attr.addAttribute("nonce", appleOauth2Property.getNonce());
        attr.addAttribute("response_type", appleOauth2Property.getResponseType());
        attr.addAttribute("scope", appleOauth2Property.getScope());
        attr.addAttribute("response_mode", appleOauth2Property.getResponseMode());

        return "redirect:" + appleOauth2Property.getAuthorizationUri();
    }

    @PostMapping("/apple/redirect")
    public String authorizationRedirect(final AppleOauth2LoginResponse response) {
        if (response == null) {
            return "redirect:/login/oauth2/fail";
        }

        final AppleOAuth2Token appleOAuth2Token = new AppleOAuth2Token(response.getId_token());

        Member member = appleOauth2LoginService.getMemberInfo(appleOAuth2Token);
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, SessionMember.from(member));
        httpSession.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, appleOAuth2Token.getEmail());

        return "redirect:/";
    }
}