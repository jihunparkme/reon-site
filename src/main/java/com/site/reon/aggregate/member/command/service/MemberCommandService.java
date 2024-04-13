package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.dto.MemberEditRequest;
import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.aggregate.member.command.dto.ApiRegisterMemberSerialNo;
import com.site.reon.aggregate.member.service.dto.SignUpDto;

public interface MemberCommandService {
    void signUpWithEmail(SignUpDto signUpDto);

    void update(final MemberEditRequest memberEditRequest, final Long id);

    boolean registerSerialNo(final long id, final ApiRegisterMemberSerialNo request);

    boolean withdraw(final WithdrawRequest request);
}
