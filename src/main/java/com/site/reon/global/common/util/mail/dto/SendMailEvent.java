package com.site.reon.global.common.util.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SendMailEvent {
    private String subject;
    private String contents;
    private Optional<List<String>> addressList;
}
