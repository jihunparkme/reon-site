package com.site.reon.global.common.constant.mail;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MailSubject {
    AUTH_CODE("Please verify your email"),
    INQUIRY("Inquiry has been registered."),
    ;

    private final String title;

    public String title() {
        return title;
    }
}
