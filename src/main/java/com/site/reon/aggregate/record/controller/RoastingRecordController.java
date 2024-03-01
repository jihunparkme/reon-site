package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.service.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.domain.RoastingRecord;
import com.site.reon.aggregate.record.service.RoastingRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.site.reon.global.common.constant.Result.FAIL;
import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@Controller
@RequestMapping("/record")
@RequiredArgsConstructor
public class RoastingRecordController {

    private final RoastingRecordService recordService;

    @GetMapping
    public String list(
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
            Model model) {

        // TODO: 로그인 정보 확인해서 관리자면 전체 조회, 일반 회원이면 회원 정보만 조회

        // TODO: 회원번호, 날짜로 검색

        final var roastingRecordListPage = recordService.findAllSortByIdDescPaging(page, size);

        model.addAttribute("roastingRecordListPage", roastingRecordListPage);
        model.addAttribute("page", page);

        return "record/record-list";
    }

    @GetMapping("{id}")
    public String view(@PathVariable(name = "id") final Long id, Model model) {
        model.addAttribute("record", recordService.findRoastingRecordBy(id));
        return "record/record-view";
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody final RoastingRecordRequest request){
        try {
            recordService.upload(request);
            return new ResponseEntity(SUCCESS, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("RoastingRecordController.uploadFile Exception: ", e);
            return new ResponseEntity<>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
