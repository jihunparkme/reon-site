package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.ApiEmailVerifyDto;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;

public interface MemberLoginService {
    void signup(SignUpDto memberDto);

    boolean verifyEmail(ApiEmailVerifyDto emailVerityDto);

    void emailAuthenticate(LoginDto loginDto);
}
