package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;

public interface MemberLoginService {
    void signUpWithEmail(SignUpDto memberDto);

    void emailAuthenticate(LoginDto loginDto);

    /**
     * 소셜 로그인 가입 여부 확인 for App
     */
    boolean verifySocialEmail(ApiEmailVerifyRequest request);
}
