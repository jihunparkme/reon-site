package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import org.springframework.http.ResponseCookie;

public interface MemberService {
    void signup(SignUpDto memberDto);
    ResponseCookie getCookie(LoginDto loginDto);
    MemberDto getMemberWithAuthorities(String email);
    MemberDto getMyMemberWithAuthorities();
}
