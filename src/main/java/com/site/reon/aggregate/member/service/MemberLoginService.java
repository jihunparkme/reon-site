package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.*;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiOAuth2SignUpRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiWithdrawRequest;

public interface MemberLoginService {
    /**
     * 이메일 가입
     */
    void signup(SignUpDto memberDto);

    /**
     * 소셜 로그인 가입 여부 확인 for App
     */
    boolean verifySocialEmail(ApiEmailVerifyRequest request);

    /**
     * 이메일 로그인 검증
     */
    void emailAuthenticate(LoginDto loginDto);

    /**
     * 신규 소셜 가입 for App
     */
    MemberDto oAuth2SignUp(ApiOAuth2SignUpRequest request);

    /**
     * 회원 탈퇴
     */
    boolean withdraw(ApiWithdrawRequest request);
}
