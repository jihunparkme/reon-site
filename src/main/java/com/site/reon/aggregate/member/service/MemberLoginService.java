package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.*;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiOAuth2SignUpRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiWithdrawRequest;

public interface MemberLoginService {
    void signup(SignUpDto memberDto);

    boolean verifyEmail(ApiEmailVerifyRequest request);

    void emailAuthenticate(LoginDto loginDto);

    MemberDto oAuth2SignUp(ApiOAuth2SignUpRequest request);

    boolean withdraw(ApiWithdrawRequest request);
}
