package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.aggregate.member.infra.service.GoogleOAuth2WithdrawalService;
import com.site.reon.global.common.dto.BasicResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
public class GoogleOAuth2WithdrawalController {

    private final GoogleOAuth2WithdrawalService googleOAuth2WithdrawalService;

    @PostMapping("/withdraw/google/signal")
    public ResponseEntity signalWithdrawal(@Valid @RequestBody final WithdrawRequest request) {
        googleOAuth2WithdrawalService.signalWithdrawal(request);
        return BasicResponse.ok(SUCCESS);
    }
}

