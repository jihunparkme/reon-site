package com.site.reon.domain.member.conrtoller;

import com.site.reon.domain.member.dto.MemberDto;
import com.site.reon.domain.member.dto.SignUpDto;
import com.site.reon.domain.member.service.MemberService;
import com.site.reon.global.security.exception.DuplicateMemberException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/sign-in")
    public String signIn() {
        return "login/login";
    }

    @PostMapping("/sign-up")
    public ResponseEntity signup(@Valid @RequestBody SignUpDto signUpDto) {
        try {
            memberService.signup(signUpDto);
            return ResponseEntity.ok(new MemberDto());
        } catch (DuplicateMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("회원가입을 실패하였습니다. 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDto> getMyMemberInfo() {
        return ResponseEntity.ok(memberService.getMyMemberWithAuthorities());
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberDto> getMemberInfo(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberWithAuthorities(email));
    }
}