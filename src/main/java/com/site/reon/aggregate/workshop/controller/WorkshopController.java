package com.site.reon.aggregate.workshop.controller;

import com.site.reon.aggregate.workshop.command.service.WorkshopService;
import com.site.reon.global.common.dto.BasicResponse;
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
    public ResponseEntity deleteWorkshop(@PathVariable(name = "id") final Long id) {
        workshopService.delete(id);
        return BasicResponse.ok(true);
    }
}
