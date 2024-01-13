package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;

public interface MemberService {
    void signup(SignUpDto memberDto);
    MemberDto getMemberWithAuthorities(String email);
    MemberDto getMyMemberWithAuthorities();
}
