package com.site.reon.aggregate.member.query.service;

import com.site.reon.aggregate.member.query.dto.ApiEmailVerifyRequest;

public interface MemberFindApiService {
    boolean verifySocialEmail(ApiEmailVerifyRequest request);
}
