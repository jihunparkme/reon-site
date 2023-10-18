package com.site.reon.domain.record.web;

import com.site.reon.domain.record.entity.RoastingRecord;
import com.site.reon.domain.record.service.RoastingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/record")
@RequiredArgsConstructor
public class RoastingRecordController {

    private final RoastingRecordService recordService;

    @GetMapping
    public String list(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "6") Integer size,
            Model model) {

        Page<RoastingRecord> roastingRecordListPage = recordService.findAllSortByIdDescPaging(page, size);

        model.addAttribute("roastingRecordListPage", roastingRecordListPage);
        model.addAttribute("page", page);

        return "record/record-list";
    }
}
