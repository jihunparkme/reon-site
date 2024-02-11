package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.MemberEditRequest;
import com.site.reon.global.common.annotation.LoginMember;
import com.site.reon.global.security.dto.SessionMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/mypage")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String view(@LoginMember SessionMember session, Model model) {
        MemberDto findMember = memberService.getMember(session.getId());
        model.addAttribute("member", findMember);
        return "member/mypage";
    }

    @PostMapping("/mypage/edit")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String edit(@Valid @ModelAttribute("member") MemberEditRequest request,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "member/mypage";
        }

        memberService.update(request);

        redirectAttributes.addAttribute("status", true);
        return "redirect:/member/mypage";
    }

//    @GetMapping("/{email}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<MemberDto> getMemberInfo(@PathVariable String email) {
//        return ResponseEntity.ok(MemberDto.from(memberService.getMemberWithAuthorities(email)));
//    }
}