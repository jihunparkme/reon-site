package com.site.reon.aggregate.member.controller;

import com.site.reon.global.common.property.AppleOauth2Property;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class MemberAppleOauth2LoginController {

    private final AppleOauth2Property appleOauth2Property;

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
}