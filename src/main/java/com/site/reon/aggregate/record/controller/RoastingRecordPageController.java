package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.command.service.RoastingRecordCommandService;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.service.RoastingRecordFindService;
import com.site.reon.global.common.annotation.LoginMember;
import com.site.reon.global.security.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@Controller
@RequestMapping("/record")
@RequiredArgsConstructor
public class RoastingRecordPageController {

    private final RoastingRecordFindService roastingRecordFindService;
    private final RoastingRecordCommandService roastingRecordCommandService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String list(
            @LoginMember final SessionMember session,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
            Model model) {
        // TODO: 날짜, 제목 검색
        final long memberId = session.getId();
        final var roastingRecordListPage = roastingRecordFindService.findAllByMemberIdOrderByIdDescPaging(memberId, page, size);

        model.addAttribute("roastingRecordListPage", roastingRecordListPage);
        model.addAttribute("page", page);

        return "record/record-list";
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public String view(@LoginMember final SessionMember session,
                       @PathVariable(name = "id") final Long id,
                       Model model) {
        final Long memberId = session.getId();
        final RoastingRecord roastingRecord = roastingRecordFindService.findRoastingRecordBy(id, memberId);
        model.addAttribute("record", RoastingRecordResponse.of(roastingRecord));
        return "record/record-view";
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody final RoastingRecordRequest request){
        roastingRecordCommandService.upload(request);
        return new ResponseEntity(SUCCESS, HttpStatus.CREATED);
    }
}
