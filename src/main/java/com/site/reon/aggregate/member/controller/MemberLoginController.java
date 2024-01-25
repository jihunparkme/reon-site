package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.security.dto.SessionMember;
import com.site.reon.global.security.exception.DuplicateMemberException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class MemberLoginController {
    private final MemberService memberService;
    private final MemberLoginService memberLoginService;
    private final HttpSession httpSession;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping
    public String login(HttpServletRequest request) {
        request.getSession().setAttribute(SessionConst.LOGIN_PREV_PAGE, request.getHeader("Referer"));
        return "login/login";
    }

    @GetMapping("/email")
    public String signIn() {
        return "login/email";
    }

    @PostMapping("/email/sign-up")
    public ResponseEntity signup(@Valid @RequestBody SignUpDto signUpDto) {
        try {
            memberLoginService.signup(signUpDto);
            return ResponseEntity.ok(new MemberDto());
        } catch (DuplicateMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("회원가입을 실패하였습니다. 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/email/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Member member = memberService.getMemberWithAuthorities(loginDto.getEmail());
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, SessionMember.from(member));
        httpSession.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, loginDto.getEmail());

        return ResponseEntity.ok(MemberDto.from(member));
    }

    @GetMapping("/oauth2/fail")
    public String oauth2Fail() {
        return "login/oauth2-fail";
    }
}
