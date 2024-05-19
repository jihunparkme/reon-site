package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.controller.dto.MemberSearchRequestParam;
import com.site.reon.aggregate.member.query.service.MemberFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/members")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class MemberAdminPageController {

    private final MemberFindService memberFindService;

    @GetMapping
    public String findMembers(@ModelAttribute MemberSearchRequestParam param,
                              Model model) {
        final var memberListPage = memberFindService.findAll(param);

        model.addAttribute("memberListPage", memberListPage);
        model.addAttribute("page", param.getPage());

        return "admin/members/member-list";
    }
}
