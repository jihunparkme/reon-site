package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.dto.api.ApiDeleteRecordRequest;
import com.site.reon.aggregate.record.command.dto.api.ApiRoastingRecordUploadRequest;
import com.site.reon.aggregate.record.command.service.RoastingRecordCommandService;
import com.site.reon.aggregate.record.query.dto.RoastingRecordListResponse;
import com.site.reon.aggregate.record.query.dto.api.ApiRoastingRecordsRequest;
import com.site.reon.aggregate.record.query.dto.api.ApiRoastingRecordResponse;
import com.site.reon.aggregate.record.query.dto.api.RoastingRecordsAndPilotsResponse;
import com.site.reon.aggregate.record.query.service.RoastingRecordFindService;
import com.site.reon.aggregate.record.query.service.RoastingRecordShareService;
import com.site.reon.global.common.dto.ApiRequest;
import com.site.reon.global.common.dto.BasicResponse;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RoastingRecordApiController {

    private final RoastingRecordFindService roastingRecordFindService;
    private final RoastingRecordCommandService roastingRecordCommandService;
    private final RoastingRecordShareService roastingRecordShareService;

    @ApiOperation(value = "로스팅 로그 리스트 조회", notes = "앱에서 로스팅 로그 리스트를 조회합니다.")
    @PostMapping
    public ResponseEntity list(@Valid @RequestBody final ApiRoastingRecordsRequest request) {
        final List<RoastingRecordListResponse> result = roastingRecordFindService.findRoastingRecordListBy(request.getMemberId());
        return BasicResponse.ok(result);
    }

    @ApiOperation(value = "로스팅 로그 리스트 조회(파일럿 로그 포함)", notes = "앱에서 파일럿 로그를 포함한 로스팅 로그 리스트를 조회합니다.")
    @PostMapping("/contain/pilot")
    public ResponseEntity listContainPilots(@Valid @RequestBody final ApiRoastingRecordsRequest request) {
        final RoastingRecordsAndPilotsResponse result = roastingRecordFindService.findRoastingRecordsAndPilotsBy(request.getMemberId());
        return BasicResponse.ok(result);
    }

    @ApiOperation(value = "로스팅 로그 조회", notes = "앱에서 로스팅 로그를 조회합니다.")
    @PostMapping("/{id}")
    public ResponseEntity view(@PathVariable(name = "id") final Long id,
                               @Valid @RequestBody final ApiRoastingRecordsRequest request) {
        final RoastingRecord roastingRecord = roastingRecordFindService.findRoastingRecordBy(
                id, request.getMemberId(), request.isPilot());
        return BasicResponse.ok(ApiRoastingRecordResponse.of(roastingRecord));
    }

    @ApiOperation(value = "로스팅 로그 업로드", notes = "앱에서 로스팅 로그를 업로드합니다.")
    @PostMapping("/upload")
    public ResponseEntity upload(@Valid @RequestBody final ApiRoastingRecordUploadRequest request) {
        roastingRecordCommandService.upload(request);
        return BasicResponse.created(SUCCESS);
    }

    @ApiOperation(value = "로스팅 로그 공유", notes = "앱에서 로스팅 로그를 공유합니다.")
    @PostMapping("/{id}/share")
    public ResponseEntity share(@PathVariable(name = "id") final Long id,
                                @RequestParam(value = "email") final String email,
                                @Valid @RequestBody final ApiRequest request) {
        final ApiRoastingRecordResponse result = roastingRecordShareService.findRoastingRecordByIdAndEmail(id, email);
        return BasicResponse.ok(result);
    }

    @ApiOperation(value = "로스팅 로그 삭제", notes = "앱에서 로스팅 로그를 삭제합니다.")
    @PostMapping("/{id}/delete")
    public ResponseEntity delete(@PathVariable(name = "id") final Long id,
                                @Valid @RequestBody final ApiDeleteRecordRequest request) {
        roastingRecordCommandService.deleteRecord(id, request.getMemberId());
        return BasicResponse.ok(SUCCESS);
    }
}