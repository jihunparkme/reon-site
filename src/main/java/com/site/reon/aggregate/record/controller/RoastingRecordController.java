package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.command.dto.DeleteRecordRequest;
import com.site.reon.aggregate.record.command.dto.UpdateRecordRequest;
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
@PreAuthorize("isAuthenticated()")
public class RoastingRecordController {

    private final RoastingRecordCommandService roastingRecordCommandService;

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecord(@PathVariable(name = "id") final Long id,
                                       @Valid @RequestBody final DeleteRecordRequest request) {
        roastingRecordCommandService.deleteRecord(id, request.getMemberId());
        return BasicResponse.ok(SUCCESS);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMemo(@PathVariable(name = "id") final Long id,
                                     @Valid @RequestBody final UpdateRecordRequest request) {
        roastingRecordCommandService.updateMemo(id, request);
        return BasicResponse.ok(SUCCESS);
    }
}
