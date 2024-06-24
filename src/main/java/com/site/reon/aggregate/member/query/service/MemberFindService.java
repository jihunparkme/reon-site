package com.site.reon.aggregate.member.query.service;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.query.dto.MemberResponse;
import com.site.reon.aggregate.member.query.dto.MemberSearchRequestParam;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberFindService {
    List<Member> getMemberWithAuthorities(String email);

    Member getMemberWithAuthorities(String email, OAuth2Client oAuthClient);

    MemberResponse getMember(long id);

    Page<Member> findAll(MemberSearchRequestParam param);

    Page<Member> findAllByFilter(MemberSearchRequestParam param);
}
