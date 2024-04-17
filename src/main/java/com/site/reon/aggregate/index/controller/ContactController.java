package com.site.reon.aggregate.index.controller;

import com.site.reon.aggregate.index.dto.ContactMailRequest;
import com.site.reon.aggregate.index.service.ContactService;
import com.site.reon.global.common.dto.BasicResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/email")
    public ResponseEntity contactMail(@Valid @RequestBody final ContactMailRequest contactMailRequest) {
        contactService.contactMail(contactMailRequest);
        return BasicResponse.ok(SUCCESS);
    }
}
