package com.site.reon.domain.member.service;

import com.site.reon.domain.member.dto.MemberDto;

public interface MemberService {
    MemberDto signup(MemberDto memberDto);
    MemberDto getMemberWithAuthorities(String email);
    MemberDto getMyMemberWithAuthorities();
}
