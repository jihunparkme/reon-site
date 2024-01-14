package com.site.reon.aggregate.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberLoginController {

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/sign-in")
    public String signIn() {
        return "login/email";
    }
}
