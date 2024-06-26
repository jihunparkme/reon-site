package com.site.reon.aggregate.member.controller.oauth2;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.controller.dto.AppleOauth2LoginResponse;
import com.site.reon.aggregate.member.query.dto.AppleOAuth2Token;
import com.site.reon.aggregate.member.query.service.AppleOAuth2MemberFindService;
import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.common.property.AppleOAuth2Property;
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
public class AppleOAuth2LoginController {

    private final HttpSession httpSession;
    private final AppleOAuth2Property appleOAuth2Property;
    private final AppleOAuth2MemberFindService appleOAuth2MemberFindService;

    @GetMapping(value = "/authorization/apple")
    public String authorization(RedirectAttributes attr) {
        attr.addAttribute("client_id", appleOAuth2Property.getClientId());
        attr.addAttribute("redirect_uri", appleOAuth2Property.getRedirectUri());
        attr.addAttribute("nonce", appleOAuth2Property.getNonce());
        attr.addAttribute("response_type", appleOAuth2Property.getResponseType());
        attr.addAttribute("scope", appleOAuth2Property.getScope());
        attr.addAttribute("response_mode", appleOAuth2Property.getResponseMode());

        return "redirect:" + appleOAuth2Property.getAuthorizationUri();
    }

    @PostMapping("/apple/redirect")
    public String authorizationRedirect(final AppleOauth2LoginResponse response) {
        if (response == null || response.getId_token() == null) {
            return "redirect:/login/oauth2/fail";
        }

        final AppleOAuth2Token appleOAuth2Token = new AppleOAuth2Token(response.getId_token());

        Member member = appleOAuth2MemberFindService.getMemberInfo(appleOAuth2Token);
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, SessionMember.from(member));
        httpSession.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, appleOAuth2Token.getEmail());

        return "redirect:/";
    }
}