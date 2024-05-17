package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.service.RoastingRecordFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/admin/records")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class RoastingRecordAdminPageController {

    private final RoastingRecordFindService recordService;

    @GetMapping
    public String list(
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
            Model model) {
        // TODO: 날짜, 제목, 시리얼넘버 검색
        final var roastingRecordListPage = recordService.findAllOrderByIdDescPaging(page, size);

        model.addAttribute("roastingRecordListPage", roastingRecordListPage);
        model.addAttribute("page", page);
        return "admin/records/record-list";
    }

    @GetMapping("{id}")
    public String view(@PathVariable(name = "id") final Long id,
                       Model model) {
        final RoastingRecordResponse roastingRecord = recordService.findRoastingRecordBy(id);

        model.addAttribute("record", roastingRecord);
        return "admin/records/record-view";
    }
}
