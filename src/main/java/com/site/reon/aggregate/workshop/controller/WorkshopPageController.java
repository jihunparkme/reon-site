package com.site.reon.aggregate.workshop.controller;

import com.site.reon.aggregate.member.command.dto.MemberEditRequest;
import com.site.reon.aggregate.workshop.command.domain.Workshop;
import com.site.reon.aggregate.workshop.command.dto.SaveWorkshopPageRequest;
import com.site.reon.aggregate.workshop.command.dto.WorkshopSaveRequest;
import com.site.reon.aggregate.workshop.command.service.WorkshopService;
import com.site.reon.global.common.annotation.LoginMember;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.dto.SessionMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/workshop")
@RequiredArgsConstructor
public class WorkshopPageController {
    @PostMapping("/share")
    @PreAuthorize("isAuthenticated()")
    public String sharePage(@ModelAttribute("recordId") String recordId,
                            Model model) {
        model.addAttribute("workshop", new Workshop());
        model.addAttribute("recordId", recordId);
        return "workshop/workshop-save";
    }
}
