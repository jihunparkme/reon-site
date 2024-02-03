package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.ApiEmailVerifyDto;
import com.site.reon.aggregate.member.service.dto.ApiLoginDto;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.global.common.constant.Result;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class MemberLoginApiController {

    private final MemberLoginService memberLoginService;
    private final MemberService memberService;

    @PostMapping("/verify/email")
    public ResponseEntity verifyEmail(@Valid @RequestBody ApiEmailVerifyDto apiEmailVerifyDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(allErrors)) {
                return BasicResponse.clientError(allErrors.get(0).getDefaultMessage());
            }
            return BasicResponse.clientError(Result.FAIL.message());
        }

        try {
            boolean result = memberLoginService.verifyEmail(apiEmailVerifyDto);
            return BasicResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberLoginApiController.verifyEmail Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }

    @PostMapping("/email")
    public ResponseEntity loginEmail(@Valid @RequestBody ApiLoginDto apiLoginDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(allErrors)) {
                return BasicResponse.clientError(allErrors.get(0).getDefaultMessage());
            }
            return BasicResponse.clientError(Result.FAIL.message());
        }

        try {
            memberLoginService.emailAuthenticate(LoginDto.from(apiLoginDto));
            Member member = memberService.getMemberWithAuthorities(apiLoginDto.getEmail(), OAuth2Client.EMPTY);
            return BasicResponse.ok(MemberDto.from(member));
        } catch (BadCredentialsException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberLoginApiController.loginEmail Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }
}
