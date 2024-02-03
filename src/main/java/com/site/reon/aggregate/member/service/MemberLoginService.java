package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.*;

public interface MemberLoginService {
    void signup(SignUpDto memberDto);

    boolean verifyEmail(ApiEmailVerifyDto emailVerityDto);

    void emailAuthenticate(LoginDto loginDto);

    MemberDto oAuth2SignUp(ApiOAuth2SignUp apiOAuth2SignUp);
}
