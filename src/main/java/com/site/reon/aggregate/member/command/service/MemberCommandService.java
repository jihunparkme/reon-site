package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.service.dto.MemberEditRequest;

public interface MemberCommandService {
    void update(MemberEditRequest memberEditRequest, Long id);
}
