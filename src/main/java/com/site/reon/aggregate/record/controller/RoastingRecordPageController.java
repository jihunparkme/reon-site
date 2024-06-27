package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.command.domain.RoastingRecord;
import com.site.reon.aggregate.record.command.dto.RoastingRecordRequest;
import com.site.reon.aggregate.record.command.service.RoastingRecordCommandService;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.dto.RecordSearchRequestParam;
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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    public String list(@LoginMember final SessionMember session,
                       @ModelAttribute RecordSearchRequestParam param,
                       Model model) {
        final long memberId = session.getId();
        final var roastingRecordListPage = roastingRecordFindService.findAllByFilter(memberId, param);

        model.addAttribute("roastingRecordListPage", roastingRecordListPage);
        model.addAttribute("page", param.getPage());

        return "record/record-list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String view(@LoginMember final SessionMember session,
                       @PathVariable(name = "id") final Long id,
                       Model model) {
        final Long memberId = session.getId();
        final RoastingRecord roastingRecord = roastingRecordFindService.findRoastingRecordBy(id, memberId);
        model.addAttribute("record", RoastingRecordResponse.of(roastingRecord));
        return "record/record-view";
    }

    @GetMapping("/compare")
    @PreAuthorize("isAuthenticated()")
    public String view(@LoginMember final SessionMember session,
                       @RequestParam(name = "id") final List<Long> ids,
                       Model model) {
        final Long memberId = session.getId();
        final List<RoastingRecord> roastingRecords = roastingRecordFindService.findRoastingRecordBy(ids, memberId);
        final Collection<RoastingRecordResponse> response = roastingRecords.stream()
                .map(RoastingRecordResponse::of)
                .collect(Collectors.toList());
        model.addAttribute("records", response);
        return "record/record-compare";
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody final RoastingRecordRequest request){
        roastingRecordCommandService.upload(request);
        return new ResponseEntity(SUCCESS, HttpStatus.CREATED);
    }
}
