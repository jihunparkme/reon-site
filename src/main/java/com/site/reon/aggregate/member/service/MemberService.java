package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.dto.MemberDto;

public interface MemberService {
    Member getMemberWithAuthorities(String email);
    MemberDto getMemberDtoWithAuthorities(String email);
    MemberDto getMyMemberWithAuthorities();
}
