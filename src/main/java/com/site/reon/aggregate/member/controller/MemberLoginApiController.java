package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.MemberAuthCodeService;
import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.api.*;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.common.util.BindingResultUtil;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class MemberLoginApiController {

    private final MemberLoginService memberLoginService;
    private final MemberService memberService;
    private final MemberAuthCodeService memberAuthCodeService;

    @ApiOperation(value = "소셜 로그인 가입 여부 확인", notes = "앱에서 소셜 로그인 가입 여부를 확인합니다.")
    @PostMapping("/verify/email")
    public ResponseEntity verifyEmail(@Valid @RequestBody ApiEmailVerifyRequest request,
                                      BindingResult bindingResult) {
        ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            boolean result = memberLoginService.verifySocialEmail(request);
            return BasicResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberLoginApiController.verifyEmail Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }

    @ApiOperation(value = "신규 소셜 가입", notes = "앱에서 신규로 소셜 가입을 합니다.")
    @PostMapping("/oauth2/sign-up")
    public ResponseEntity signUpOAuth2(@Valid @RequestBody ApiOAuth2SignUpRequest request,
                                       BindingResult bindingResult) {
        ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            MemberDto member = memberLoginService.oAuth2SignUp(request);
            return BasicResponse.ok(member);
        } catch (DuplicateMemberException | IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberLoginApiController.oAuth2SignUp Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }

    @ApiOperation(value = "이메일 가입", notes = "앱에서 이메일로 가입합니다.")
    @PostMapping("/email/sign-up")
    public ResponseEntity signUpEmail(@Valid @RequestBody ApiSignUpRequest request,
                                     BindingResult bindingResult) {
        ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            memberLoginService.signup(request);
            return BasicResponse.ok(SUCCESS);
        } catch (DuplicateMemberException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            return BasicResponse.internalServerError("회원가입을 실패하였습니다. 다시 시도해 주세요.");
        }
    }

    @ApiOperation(value = "이메일 인증번호 발송", notes = "앱에서 이메일로 가입 시 인증번호를 발송합니다.")
    @PostMapping("/email/auth-code")
    public ResponseEntity sendAuthenticationCodeByEmail(@Valid @RequestBody EmailAuthCodeRequest request,
                                      BindingResult bindingResult) {
        ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            memberAuthCodeService.sendAuthenticationCodeByEmail(KeyPrefix.SIGN_UP, request.getPurpose(), request.getEmail());
            return BasicResponse.ok(SUCCESS);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            return BasicResponse.internalServerError("이메일 인증번호 발송을 실패하였습니다. 다시 시도해 주세요.");
        }
    }

    @ApiOperation(value = "이메일 인증번호 검증", notes = "앱에서 이메일로 가입 시 발송된 인증번호를 검증합니다.")
    @PostMapping("/email/auth-code/verify")
    public ResponseEntity verifyAuthenticationCode(@Valid @RequestBody EmailAuthCodeVerifyRequest request,
                                                        BindingResult bindingResult) {
        ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            memberAuthCodeService.verifyAuthenticationCode(KeyPrefix.SIGN_UP, request.getEmail(), request.getAuthCode());
            return BasicResponse.ok(SUCCESS);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            return BasicResponse.internalServerError("이메일 인증번호 검증을 실패하였습니다. 다시 시도해 주세요.");
        }
    }

    @ApiOperation(value = "이메일 로그인", notes = "앱에서 이메일로 로그인합니다.")
    @PostMapping("/email")
    public ResponseEntity loginEmail(@Valid @RequestBody ApiLoginRequest request,
                                     BindingResult bindingResult) {
        ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            memberLoginService.emailAuthenticate(LoginDto.from(request));
            Member member = memberService.getMemberWithAuthorities(request.getEmail(), OAuth2Client.EMPTY);
            return BasicResponse.ok(MemberDto.from(member));
        } catch (BadCredentialsException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberLoginApiController.loginEmail Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }

    @ApiOperation(value = "회원 탈퇴", notes = "앱에서 회원을 탈퇴합니다.")
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@Valid @RequestBody ApiWithdrawRequest request,
                                      BindingResult bindingResult) {
        ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            boolean result = memberLoginService.withdraw(request);
            return BasicResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberLoginApiController.withdraw Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }
}
