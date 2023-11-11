package com.site.reon.domain.member.conrtoller;

import com.site.reon.domain.member.constant.AuthConst;
import com.site.reon.domain.member.dto.LoginDto;
import com.site.reon.domain.member.dto.MemberDto;
import com.site.reon.domain.member.service.MemberService;
import com.site.reon.global.security.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;

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
}