package com.site.reon.aggregate.member.controller;

import com.site.reon.global.common.constant.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class MemberLoginPageController {

    @GetMapping
    public String loginMainPage(HttpServletRequest request) {
        request.getSession().setAttribute(SessionConst.LOGIN_PREV_PAGE, request.getHeader("Referer"));
        return "login/login";
    }

    @GetMapping("/email")
    public String emailLoginPage() {
        return "login/email";
    }

    @GetMapping("/oauth2/fail")
    public String oauth2LoginFailPage() {
        return "login/oauth2-fail";
    }
}
