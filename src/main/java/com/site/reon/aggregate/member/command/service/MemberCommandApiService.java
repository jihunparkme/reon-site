package com.site.reon.aggregate.member.command.service;

import com.site.reon.aggregate.member.command.dto.ApiOAuth2SignUpRequest;
import com.site.reon.aggregate.member.query.dto.MemberDto;

public interface MemberCommandApiService {
    MemberDto oAuth2SignUp(ApiOAuth2SignUpRequest request);
}
