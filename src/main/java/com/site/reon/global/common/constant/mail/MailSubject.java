package com.site.reon.global.common.constant.mail;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MailSubject {
    AUTH_CODE("인증번호 발송"),
    INQUIRY("문의 등록 안내"),
    ;

    private final String title;

    public String title() {
        return title;
    }
}
