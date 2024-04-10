package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.MemberAuthCodeService;
import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.*;
import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.dto.SessionMember;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/login/email")
@RequiredArgsConstructor
public class MemberEmailLoginController {
    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MemberLoginService memberLoginService;
    private final MemberAuthCodeService memberAuthCodeService;

    @PostMapping("/sign-up")
    public ResponseEntity signup(@Valid @RequestBody final SignUpDto signUpDto) {
        memberLoginService.signup(signUpDto);
        return ResponseEntity.ok(SUCCESS);
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> authorize(@Valid @RequestBody final LoginDto loginDto) {
        memberLoginService.emailAuthenticate(loginDto);

        final Member member = memberService.getMemberWithAuthorities(loginDto.getEmail(), OAuth2Client.EMPTY);
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, SessionMember.from(member));
        httpSession.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, loginDto.getEmail());

        return ResponseEntity.ok(MemberDto.from(member));
    }

    @PostMapping("/auth-code")
    public ResponseEntity sendAuthenticationCodeByEmail(@Valid @RequestBody final EmailAuthCodeRequest request) {
        memberAuthCodeService.sendAuthenticationCodeByEmail(KeyPrefix.SIGN_UP, KeyPrefix.SIGN_UP.purpose(), request.getEmail());
        return BasicResponse.ok(SUCCESS);
    }

    @PostMapping("/auth-code/verify")
    public ResponseEntity verifyAuthenticationCode(@Valid @RequestBody final EmailAuthCodeVerifyRequest request) {
        memberAuthCodeService.verifyAuthenticationCode(KeyPrefix.SIGN_UP, request.getEmail(), request.getAuthCode());
        return BasicResponse.ok(SUCCESS);
    }
}
