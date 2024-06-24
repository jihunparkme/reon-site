package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.command.dto.MemberEditRequest;
import com.site.reon.aggregate.member.command.dto.WithdrawRequest;
import com.site.reon.aggregate.member.command.service.MemberCommandService;
import com.site.reon.aggregate.member.query.dto.MemberResponse;
import com.site.reon.aggregate.member.query.service.MemberFindService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MemberMypageController {
    private final MemberFindService memberFindService;
    private final MemberCommandService memberCommandService;

    @GetMapping("/mypage")
    public String view(@LoginMember final SessionMember session, Model model) {
        final MemberResponse findMember = memberFindService.getMember(session.getId());
        model.addAttribute("member", findMember);
        return "member/mypage";
    }

    @PostMapping("/mypage/edit")
    public String edit(@Valid @ModelAttribute("member") final MemberEditRequest request,
                       final BindingResult bindingResult,
                       @LoginMember final SessionMember session,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "member/mypage";
        }

        try {
            memberCommandService.update(request, session.getId());
        } catch (Exception e) {
            bindingResult.reject("global.error", e.getMessage());
            return "member/mypage";
        }

        redirectAttributes.addAttribute("status", true);
        return "redirect:/member/mypage";
    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@Valid @RequestBody final WithdrawRequest request) {
        final boolean result = memberCommandService.withdraw(request);
        return BasicResponse.ok(result);
    }
}