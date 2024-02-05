package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.*;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyRequest;
import com.site.reon.aggregate.member.service.dto.api.ApiOAuth2SignUpRequest;

public interface MemberLoginService {
    void signup(SignUpDto memberDto);

    boolean verifyEmail(ApiEmailVerifyRequest request);

    void emailAuthenticate(LoginDto loginDto);

    MemberDto oAuth2SignUp(ApiOAuth2SignUpRequest request);
}
