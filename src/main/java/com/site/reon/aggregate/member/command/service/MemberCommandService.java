package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.dto.MemberEditRequest;
import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.aggregate.member.command.dto.ApiRegisterMemberSerialNo;

public interface MemberCommandService {
    void update(final MemberEditRequest memberEditRequest, final Long id);

    boolean registerSerialNo(final long id, final ApiRegisterMemberSerialNo request);

    boolean withdraw(final WithdrawRequest request);
}
