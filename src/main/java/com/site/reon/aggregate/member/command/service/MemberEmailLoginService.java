package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.dto.LoginRequest;
import com.site.reon.aggregate.member.command.dto.SignUpRequest;

public interface MemberEmailLoginService {
    void signUpWithEmail(SignUpRequest memberDto);

    void emailAuthenticate(LoginRequest loginRequest);
}
