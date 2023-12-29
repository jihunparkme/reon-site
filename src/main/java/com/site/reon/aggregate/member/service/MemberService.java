package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.dto.MemberDto;
import com.site.reon.aggregate.member.dto.SignUpDto;

public interface MemberService {
    void signup(SignUpDto memberDto);
    MemberDto getMemberWithAuthorities(String email);
    MemberDto getMyMemberWithAuthorities();
}
