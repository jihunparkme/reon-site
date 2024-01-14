package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import org.springframework.http.ResponseCookie;

public interface MemberLoginService {
    void signup(SignUpDto memberDto);
    ResponseCookie getCookie(LoginDto loginDto);
}
