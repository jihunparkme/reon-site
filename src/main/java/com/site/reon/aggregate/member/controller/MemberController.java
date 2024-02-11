package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.global.common.annotation.LoginMember;
import com.site.reon.global.security.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/mypage")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String view(@LoginMember SessionMember member, Model model) {
        MemberDto findMember = memberService.getMember(member.getId());
        model.addAttribute("member", findMember);
        return "member/mypage";
    }

//    @GetMapping("/{email}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<MemberDto> getMemberInfo(@PathVariable String email) {
//        return ResponseEntity.ok(MemberDto.from(memberService.getMemberWithAuthorities(email)));
//    }
}