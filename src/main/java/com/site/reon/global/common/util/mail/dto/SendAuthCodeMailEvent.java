package com.site.reon.global.common.util.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SendAuthCodeMailEvent extends SendMailEvent {
    private String purpose;
    private String authCode;
}
