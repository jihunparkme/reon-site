package com.site.reon.aggregate.workshop.controller;

import com.site.reon.aggregate.record.command.dto.RoastingRecordsResponse;
import com.site.reon.aggregate.workshop.command.dto.WorkshopSaveRequest;
import com.site.reon.aggregate.workshop.command.service.WorkshopService;
import com.site.reon.aggregate.workshop.query.dto.WorkshopResponse;
import com.site.reon.aggregate.workshop.query.dto.WorkshopSearchRequestParam;
import com.site.reon.aggregate.workshop.query.dto.WorkshopsResponse;
import com.site.reon.aggregate.workshop.query.service.WorkshopFindService;
import com.site.reon.global.common.annotation.LoginMember;
import com.site.reon.global.security.dto.SessionMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/workshop")
@RequiredArgsConstructor
public class WorkshopPageController {

    private final WorkshopService workshopService;
    private final WorkshopFindService workshopFindService;

    @GetMapping
    public String list(@ModelAttribute WorkshopSearchRequestParam param,
                       Model model) {
        final Page<WorkshopsResponse> workshopResponsePage = workshopFindService.findAllByFilter(param);

        model.addAttribute("workshopListPage", workshopResponsePage);
        model.addAttribute("page", param.getPage());

        return "workshop/workshop-list";
    }

    @PostMapping("/share")
    @PreAuthorize("isAuthenticated()")
    public String sharePage(@ModelAttribute("recordId") long recordId,
                            Model model) {
        model.addAttribute("workshop", WorkshopSaveRequest.create(recordId));
        model.addAttribute("recordId", recordId);
        return "workshop/workshop-save";
    }

    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public String save(@Valid @ModelAttribute("workshop") final WorkshopSaveRequest request,
                       final BindingResult bindingResult,
                       @LoginMember final SessionMember session,
                       final Model model) {

        if (bindingResult.hasErrors()) {
            return "workshop/workshop-save";
        }

        try {
            final long memberId = session.getId();
            Long workshopId = workshopService.saveWorkshop(request, memberId);
            model.addAttribute("message", "saved successfully.");
            model.addAttribute("redirectUrl", "/workshop/" + workshopId);
            return "common/alertAndRedirect";
        } catch (Exception e) {
            bindingResult.reject("global.error", "Failed to write article. Please try again later.\n (" + e.getMessage() + ")");
            return "workshop/workshop-save";
        }
    }

    @GetMapping("/{id}")
    public String view(@PathVariable(name = "id") final Long id,
                       @LoginMember final SessionMember session,
                       Model model) {
        final WorkshopResponse workshop = workshopFindService.findWorkshop(id);
        final Long memberId = getMemberId(session);
        final boolean isSubscribed = workshopFindService.isSubscribed(workshop.record().getId(), memberId);

        model.addAttribute("isWriter", Objects.equals(memberId, workshop.memberId()));
        model.addAttribute("isSubscribed", isSubscribed);
        model.addAttribute("workshop", workshop);
        return "workshop/workshop-view";
    }

    private static Long getMemberId(final SessionMember session) {
        try {
            return session.getId();
        } catch (Exception e) {
            return 0L;
        }
    }
}
