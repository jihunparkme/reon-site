package com.site.reon.aggregate.member.controller;

import com.site.reon.global.common.property.GoogleOauth2Property;
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
public class MemberGoogleOauth2LoginController {

    private final GoogleOauth2Property googleOauth2Property;

    @GetMapping(value = "/authorization/google")
    public String authorization(RedirectAttributes attr) {
        attr.addAttribute("response_type", googleOauth2Property.getResponseType());
        attr.addAttribute("client_id", googleOauth2Property.getClientId());
        attr.addAttribute("scope", googleOauth2Property.getScope());
        attr.addAttribute("redirect_uri", googleOauth2Property.getRedirectUri());

        return "redirect:" + googleOauth2Property.getAuthorizationUri();
    }
}