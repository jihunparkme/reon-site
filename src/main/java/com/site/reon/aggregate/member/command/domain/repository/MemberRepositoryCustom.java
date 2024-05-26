package com.site.reon.aggregate.member.command.domain.repository;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.query.dto.MemberSearchRequestParam;
import org.springframework.data.domain.Page;

public interface MemberRepositoryCustom {
    Page<Member> findAllByFilter(MemberSearchRequestParam param);
}
