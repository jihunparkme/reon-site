package com.site.reon.domain.record.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/record")
public class RecordController {
    @GetMapping
    public String list() {
        return "record/record-list";
    }
}
