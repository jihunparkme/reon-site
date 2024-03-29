package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.MemberEditRequest;
import com.site.reon.aggregate.member.service.dto.WithdrawRequest;
import com.site.reon.global.common.annotation.LoginMember;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.dto.SessionMember;
import com.site.reon.global.security.exception.NotFoundMemberException;
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
public class MemberController {
    private final MemberService memberService;
    private final MemberLoginService memberLoginService;

    @GetMapping("/mypage")
    @PreAuthorize("isAuthenticated()")
    public String view(@LoginMember final SessionMember session, Model model) {
        final MemberDto findMember = memberService.getMember(session.getId());
        model.addAttribute("member", findMember);
        return "member/mypage";
    }

    @PostMapping("/mypage/edit")
    @PreAuthorize("isAuthenticated()")
    public String edit(@Valid @ModelAttribute("member") final MemberEditRequest request,
                       final BindingResult bindingResult,
                       @LoginMember final SessionMember session,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "member/mypage";
        }

        try {
            memberService.update(request, session.getId());
        } catch (Exception e) {
            bindingResult.reject("global.error", e.getMessage());
            return "member/mypage";
        }

        redirectAttributes.addAttribute("status", true);
        return "redirect:/member/mypage";
    }

    @PostMapping("/withdraw")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity withdraw(@Valid @RequestBody final WithdrawRequest request) {
        try {
            final boolean result = memberLoginService.withdraw(request);
            return BasicResponse.ok(result);
        } catch (IllegalArgumentException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            log.error("MemberEmailLoginController.withdraw Exception: ", e);
            return BasicResponse.internalServerError("Failed to withdraw member. Please try again.");
        }
    }
}