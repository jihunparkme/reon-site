package com.site.reon.domain.member.service;

import com.site.reon.domain.member.dto.MemberDto;
import com.site.reon.domain.member.dto.SignUpDto;

public interface MemberService {
    void signup(SignUpDto memberDto);
    MemberDto getMemberWithAuthorities(String email);
    MemberDto getMyMemberWithAuthorities();
}
