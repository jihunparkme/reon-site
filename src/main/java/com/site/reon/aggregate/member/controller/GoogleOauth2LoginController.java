package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.service.GoogleOauth2LoginService;
import com.site.reon.aggregate.member.service.dto.WithdrawRequest;
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
public class GoogleOauth2LoginController {

    private final GoogleOauth2LoginService googleOauth2LoginService;

    @PostMapping("/withdraw/google/signal")
    public ResponseEntity signalWithdrawal(@Valid @RequestBody final WithdrawRequest request) {
        googleOauth2LoginService.signalWithdrawal(request);
        return BasicResponse.ok(SUCCESS);
    }
}

