package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.command.dto.SharePilotRecordRequest;
import com.site.reon.aggregate.record.command.service.RoastingRecordCommandService;
import com.site.reon.global.common.dto.BasicResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PILOT')")
public class RoastingRecordPilotController {

    private final RoastingRecordCommandService roastingRecordCommandService;

    @PutMapping("/{id}/pilot")
    public ResponseEntity sharePilotRecord(@PathVariable(name = "id") final Long id,
                                           @Valid @RequestBody final SharePilotRecordRequest request) {
        roastingRecordCommandService.sharePilotRecord(id, request);
        return BasicResponse.ok(SUCCESS);
    }
}
