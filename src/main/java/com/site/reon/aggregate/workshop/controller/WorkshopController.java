package com.site.reon.aggregate.workshop.controller;

import com.site.reon.aggregate.workshop.command.service.WorkshopService;
import com.site.reon.global.common.annotation.LoginMember;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/workshop")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class WorkshopController {

    private final WorkshopService workshopService;

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") final Long id) {
        workshopService.delete(id);
        return BasicResponse.ok(true);
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity subscribe(@PathVariable(name = "id") final Long id,
                                    @LoginMember final SessionMember session) {
        workshopService.subscribe(id, session.getId());
        return BasicResponse.ok(true);
    }
}
