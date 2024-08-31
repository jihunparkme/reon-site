package com.site.reon.aggregate.workshop.controller;

import com.site.reon.aggregate.workshop.command.domain.Workshop;
import com.site.reon.aggregate.workshop.command.dto.WorkshopSaveRequest;
import com.site.reon.aggregate.workshop.command.service.WorkshopService;
import com.site.reon.aggregate.workshop.query.dto.WorkshopResponse;
import com.site.reon.global.common.annotation.LoginMember;
import com.site.reon.global.security.dto.SessionMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/workshop")
@RequiredArgsConstructor
public class WorkshopPageController {

    private final WorkshopService workshopService;
//    private final WorkshopFindService workshopService;

//    @GetMapping
//    public String list(@ModelAttribute WorkshopSearchRequestParam param,
//                       Model model) {
//        final var roastingRecordListPage = workshopService.findAllOrderByIdDescPaging(memberId, param);

//        model.addAttribute("workshopListPage", null);
//        model.addAttribute("page", param.getPage());

//        return "workshop/workshop-list";
//    }

    @PostMapping("/share")
    @PreAuthorize("isAuthenticated()")
    public String sharePage(@ModelAttribute("recordId") long recordId,
                            Model model) {
        model.addAttribute("workshop", WorkshopResponse.create(recordId));
        model.addAttribute("recordId", recordId);
        return "workshop/workshop-save";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("workshop") final WorkshopSaveRequest request,
                       final BindingResult bindingResult,
                       @LoginMember final SessionMember session,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "workshop/workshop-save";
        }

        try {
            final long memberId = session.getId();
            String workshopId = workshopService.saveWorkshop(request, memberId);
            redirectAttributes.addAttribute("workshopId", workshopId);
            return "redirect:/record/" + request.getRecordId();
        } catch (Exception e) {
            bindingResult.reject("global.error", e.getMessage());
            return "workshop/workshop-save";
        }
    }
}
