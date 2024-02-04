package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.*;
import com.site.reon.aggregate.member.service.dto.api.ApiEmailVerifyDto;
import com.site.reon.aggregate.member.service.dto.api.ApiOAuth2SignUpDto;

public interface MemberLoginService {
    void signup(SignUpDto memberDto);

    boolean verifyEmail(ApiEmailVerifyDto emailVerityDto);

    void emailAuthenticate(LoginDto loginDto);

    MemberDto oAuth2SignUp(ApiOAuth2SignUpDto apiOAuth2SignUp);
}
