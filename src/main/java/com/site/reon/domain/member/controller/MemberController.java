package com.site.reon.domain.member.controller;

import com.site.reon.domain.member.constant.AuthConst;
import com.site.reon.domain.member.dto.LoginDto;
import com.site.reon.domain.member.dto.MemberDto;
import com.site.reon.domain.member.dto.SignUpDto;
import com.site.reon.domain.member.service.MemberService;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/sign-in")
    public String signIn() {
        return "login/login";
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        ResponseCookie cookie = ResponseCookie.from(AuthConst.ACCESS_TOKEN, jwt)
                .httpOnly(true)
                .secure(true)
                .sameSite(AuthConst.NONE)
                .path("/")
                .build();

        MemberDto member = memberService.getMemberWithAuthorities(loginDto.getEmail());

        return ResponseEntity.ok()
                .header(AuthConst.SET_COOKIE, cookie.toString())
                .body(member);
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