package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.EmailVerifyDto;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.global.common.dto.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class MemberLoginApiController {

    private final MemberLoginService memberLoginService;
    private final MemberService memberService;

    @PostMapping("/verify/email")
    public BasicResponse verifyEmail(@RequestBody EmailVerifyDto emailVerityDto) {
        try {
            boolean result = memberLoginService.verifyEmail(emailVerityDto);
            return BasicResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return BasicResponse.internalServerError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberLoginApiController.verifyEmail Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }

    @PostMapping("/email")
    public BasicResponse loginEmail(@RequestBody LoginDto loginDto) {
        try {
            memberLoginService.emailAuthenticate(loginDto);
            Member member = memberService.getMemberWithAuthorities(loginDto.getEmail());
            return BasicResponse.ok(MemberDto.from(member));
        } catch (BadCredentialsException e) {
            return BasicResponse.internalServerError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberLoginApiController.loginEmail Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }
}
