package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.query.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.api.ApiOAuth2SignUpRequest;

public interface MemberCommandApiService {
    MemberDto oAuth2SignUp(ApiOAuth2SignUpRequest request);
}
