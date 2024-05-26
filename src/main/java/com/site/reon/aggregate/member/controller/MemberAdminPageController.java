package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.command.dto.MemberAdminEditRequest;
import com.site.reon.aggregate.member.command.service.MemberCommandService;
import com.site.reon.aggregate.member.controller.dto.MemberSearchRequestParam;
import com.site.reon.aggregate.member.query.service.MemberFindService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class MemberAdminPageController {

    private final MemberFindService memberFindService;
    private final MemberCommandService memberCommandService;

    @GetMapping
    public String findMembers(@ModelAttribute MemberSearchRequestParam param,
                              Model model) {
        final var memberListPage = memberFindService.findAll(param);

        model.addAttribute("memberListPage", memberListPage);
        model.addAttribute("page", param.getPage());

        return "admin/members/member-list";
    }

    @GetMapping("/{id}")
    public String findMember(@PathVariable(name = "id") final Long id, Model model) {
        final var member = memberFindService.getMember(id);

        model.addAttribute("member", member);

        return "admin/members/member-view";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") final Long id,
                       @Valid @ModelAttribute("member") final MemberAdminEditRequest request,
                       final BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "admin/members/member-view";
        }

        try {
            memberCommandService.update(request, id);
        } catch (Exception e) {
            bindingResult.reject("global.error", e.getMessage());
            return "admin/members/member-view";
        }

        redirectAttributes.addAttribute("status", true);
        return "redirect:/admin/members/" + id;
    }
}
