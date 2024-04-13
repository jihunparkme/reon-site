package com.site.reon.aggregate.member.service;

import com.site.reon.aggregate.member.service.dto.LoginDto;

public interface MemberLoginService {
    void emailAuthenticate(LoginDto loginDto);
}
