package com.site.reon.aggregate.record.controller;

import com.site.reon.aggregate.record.command.dto.RoastingRecordsResponse;
import com.site.reon.aggregate.record.query.dto.AdminRecordSearchRequestParam;
import com.site.reon.aggregate.record.query.dto.RoastingRecordResponse;
import com.site.reon.aggregate.record.query.service.RoastingRecordFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin/records")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class RoastingRecordAdminPageController {

    private final RoastingRecordFindService recordFindService;

    @GetMapping
    public String list(@ModelAttribute AdminRecordSearchRequestParam param,
                       Model model) {
        final Page<RoastingRecordsResponse> roastingRecordListPage = recordFindService.findAllByAdminFilter(param);

        model.addAttribute("roastingRecordListPage", roastingRecordListPage);
        model.addAttribute("page", param.getPage());

        return "admin/records/record-list";
    }

    @GetMapping("{id}")
    public String view(@PathVariable(name = "id") final Long id,
                       Model model) {
        final RoastingRecordResponse roastingRecord = recordFindService.findRoastingRecordBy(id);

        model.addAttribute("record", roastingRecord);

        return "admin/records/record-view";
    }
}
