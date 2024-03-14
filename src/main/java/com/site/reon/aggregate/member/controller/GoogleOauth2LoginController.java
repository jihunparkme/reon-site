package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.service.GoogleOauth2LoginService;
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
public class GoogleOauth2LoginController {

    private final GoogleOauth2LoginService googleOauth2LoginService;

    @PostMapping("/withdraw/google/signal")
    public ResponseEntity signalWithdrawal(@Valid @RequestBody final WithdrawRequest request) {
        try {
            googleOauth2LoginService.signalWithdrawal(request);
            return BasicResponse.ok(SUCCESS);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("GoogleOauth2LoginController.signalWithdrawal Exception: ", e);
            return BasicResponse.internalServerError("Failed to withdraw google oauth2 member. Please try again.");
        }
    }
}

