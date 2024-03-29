package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.MemberAuthCodeService;
import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.*;
import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.common.util.BindingResultUtil;
import com.site.reon.global.security.dto.SessionMember;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@Controller
@RequestMapping("/login/email")
@RequiredArgsConstructor
public class MemberEmailLoginController {
    private final HttpSession httpSession;
    private final MemberService memberService;
    private final MemberLoginService memberLoginService;
    private final MemberAuthCodeService memberAuthCodeService;

    @PostMapping("/sign-up")
    public ResponseEntity signup(@Valid @RequestBody final SignUpDto signUpDto, final BindingResult bindingResult) {
        final ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            memberLoginService.signup(signUpDto);
            return ResponseEntity.ok(SUCCESS);
        } catch (DuplicateMemberException | IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberEmailLoginController.signup Exception: ", e);
            return BasicResponse.internalServerError("Registration failed. Please try again.");
        }
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
        try {
            memberAuthCodeService.sendAuthenticationCodeByEmail(KeyPrefix.SIGN_UP, KeyPrefix.SIGN_UP.purpose(), request.getEmail());
            return BasicResponse.ok(SUCCESS);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberEmailLoginController.sendAuthenticationCodeByEmail Exception: ", e);
            return BasicResponse.internalServerError("Failed to send email authentication code. Please try again.");
        }
    }

    @PostMapping("/auth-code/verify")
    public ResponseEntity verifyAuthenticationCode(@Valid @RequestBody final EmailAuthCodeVerifyRequest request) {
        try {
            memberAuthCodeService.verifyAuthenticationCode(KeyPrefix.SIGN_UP, request.getEmail(), request.getAuthCode());
            return BasicResponse.ok(SUCCESS);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberEmailLoginController.verifyAuthenticationCode Exception: ", e);
            return BasicResponse.internalServerError("Email authentication code validation failed. Please try again.");
        }
    }
}
