package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.service.MemberGoogleOauth2LoginService;
import com.site.reon.aggregate.member.service.dto.WithdrawRequest;
import com.site.reon.global.common.dto.BasicResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@Controller
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class MemberGoogleOauth2LoginController {

    private final MemberGoogleOauth2LoginService memberGoogleOauth2Login;

    @PostMapping("/signal/withdraw/google")
    public ResponseEntity sendSignalToWithdraw(@Valid @RequestBody final WithdrawRequest request) {
        try {
            memberGoogleOauth2Login.withdraw(request);
            return BasicResponse.ok(SUCCESS);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberGoogleOauth2LoginController.sendSignalToWithdraw Exception: ", e);
            return BasicResponse.internalServerError("Failed to withdraw member. Please try again.");
        }
    }
}

