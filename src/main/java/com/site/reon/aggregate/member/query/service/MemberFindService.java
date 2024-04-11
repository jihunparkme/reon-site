package com.site.reon.aggregate.member.query.service;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.query.dto.MemberDto;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;

import java.util.List;

public interface MemberFindService {
    List<Member> getMemberWithAuthorities(String email);
    Member getMemberWithAuthorities(String email, OAuth2Client oAuthClient);
    MemberDto getMember(long id);
}
