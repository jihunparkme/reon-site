package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.MemberEditRequest;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;

import java.util.List;

public interface MemberService {
    List<Member> getMemberWithAuthorities(String email);
    Member getMemberWithAuthorities(String email, OAuth2Client oAuthClient);
    MemberDto getMember(long id);
    void update(MemberEditRequest memberEditRequest, Long id);
}
