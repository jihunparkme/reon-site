package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.MemberDto;

public interface MemberService {
    MemberDto getMemberWithAuthorities(String email);
    MemberDto getMyMemberWithAuthorities();
}
