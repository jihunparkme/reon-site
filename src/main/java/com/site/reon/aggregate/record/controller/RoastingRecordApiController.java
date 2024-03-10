package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.aggregate.record.query.dto.api.ApiRoastingRecordListRequest;
import com.site.reon.aggregate.record.query.dto.api.ApiRoastingRecordResponse;
import com.site.reon.aggregate.record.query.service.FindRoastingRecordService;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.common.util.BindingResultUtil;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RoastingRecordApiController {

    private final FindRoastingRecordService recordService;

    @ApiOperation(value = "로스팅 로그 리스트 조회", notes = "앱에서 로스팅 로그 리스트를 조회합니다.")
    @PostMapping("/list")
    public ResponseEntity RoastingRecordList(@Valid @RequestBody final ApiRoastingRecordListRequest request,
                                      final BindingResult bindingResult) {
        final ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            final List<RoastingRecordListResponse> result = recordService.findRoastingRecordListBy(request.getMemberId());
            return BasicResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("RoastingRecordApiController.RoastingRecords Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }

    @ApiOperation(value = "로스팅 로그 조회", notes = "앱에서 로스팅 로그를 조회합니다.")
    @PostMapping("/{id}")
    public ResponseEntity RoastingRecord(@PathVariable(name = "id") final Long id,
                                         @Valid @RequestBody final ApiRoastingRecordListRequest request,
                                         final BindingResult bindingResult) {
        final ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            final ApiRoastingRecordResponse result = recordService.findRoastingRecordBy(id, request.getMemberId());
            return BasicResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("RoastingRecordApiController.RoastingRecords Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }
}