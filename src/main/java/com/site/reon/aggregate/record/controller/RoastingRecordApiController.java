package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.service.RoastingRecordService;
import com.site.reon.aggregate.record.service.dto.api.ApiRoastingRecordListRequest;
import com.site.reon.aggregate.record.service.dto.RoastingRecordListResponse;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.common.util.BindingResultUtil;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class RoastingRecordApiController {

    private final RoastingRecordService recordService;

    @ApiOperation(value = "로스팅 로그 리스트 조회", notes = "앱에서 로스팅 로그 리스트를 조회합니다.")
    @PostMapping("/list")
    public ResponseEntity RoastingRecords(@Valid @RequestBody final ApiRoastingRecordListRequest request,
                                      final BindingResult bindingResult) {
        final ResponseEntity allErrors = BindingResultUtil.validateBindingResult(bindingResult);
        if (allErrors != null) return allErrors;

        try {
            final List<RoastingRecordListResponse> result = recordService.findRoastingRecordListBy(request.getEmail(), request.getAuthClientName());
            return BasicResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("RoastingRecordApiController.RoastingRecords Exception: ", e);
            return BasicResponse.internalServerError(e.getMessage());
        }
    }
}