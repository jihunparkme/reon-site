package com.site.reon.aggregate.member.controller;

import com.nimbusds.oauth2.sdk.TokenResponse;
import com.site.reon.aggregate.member.service.dto.AppleOauth2LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class MemberLoginOauth2Controller {

    @PostMapping("/apple/redirect")
    @ResponseBody
    public TokenResponse servicesRedirect(@RequestBody final AppleOauth2LoginResponse response) {
        log.error("### in servicesRedirect");
        if (response == null) {
            return null;
        }

        log.error("state: {}", response.getState());
        log.error("code: {}", response.getCode());
        log.error("idToken: {}", response.getIdToken());
        log.error("user: {}", response.getUser());

        return null;
    }
}
