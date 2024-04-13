package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;

public interface MemberLoginService {
    void signUpWithEmail(SignUpDto memberDto);

    void emailAuthenticate(LoginDto loginDto);
}
