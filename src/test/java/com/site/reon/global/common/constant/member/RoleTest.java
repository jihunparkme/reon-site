package com.site.reon.global.common.constant.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoleTest {
    @Test
    void method() throws Exception {
        Assertions.assertEquals("ADMIN", Role.ADMIN.name());
        Assertions.assertEquals("ROLE_ADMIN", Role.ADMIN.key());
        Assertions.assertEquals("ADMIN", Role.ADMIN.title());
    }
}