package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.common.constant.member.AuthConst;
import com.site.reon.global.security.exception.DuplicateMemberException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class MemberLoginController {
    private final MemberService memberService;
    private final MemberLoginService memberLoginService;

    @GetMapping
    public String login() {
        return "login/login";
    }

    @GetMapping("/email")
    public String signIn() {
        return "login/email";
    }

    @PostMapping("/email/sign-up")
    public ResponseEntity signup(@Valid @RequestBody SignUpDto signUpDto) {
        try {
            memberLoginService.signup(signUpDto);
            return ResponseEntity.ok(new MemberDto());
        } catch (DuplicateMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("회원가입을 실패하였습니다. 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/email/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        ResponseCookie cookie = memberLoginService.getCookie(loginDto);
        MemberDto member = memberService.getMemberWithAuthorities(loginDto.getEmail());

        return ResponseEntity.ok()
                .header(AuthConst.SET_COOKIE, cookie.toString())
                .header(AuthConst.AUTHORIZATION_HEADER, "Bearer " + cookie.getValue())
                .body(member);
    }

    @GetMapping("/oauth2/fail")
    public String oauth2Fail() {
        return "login/oauth2-fail";
    }
}
