package com.site.reon.aggregate.workshop.controller;

import com.site.reon.aggregate.workshop.command.WorkshopSearchRequestParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/workshop")
@RequiredArgsConstructor
public class WorkshopController {

//    private final WorkshopFindService workshopService;

    @GetMapping
    public String list(@ModelAttribute WorkshopSearchRequestParam param,
                       Model model) {
//        final var roastingRecordListPage = workshopService.findAllOrderByIdDescPaging(memberId, param);

        model.addAttribute("workshopListPage", null);
        model.addAttribute("page", param.getPage());

        return "workshop/workshop-list";
    }
}
