package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.LoginDto;
import com.site.reon.aggregate.member.service.dto.MemberDto;
import com.site.reon.aggregate.member.service.dto.SignUpDto;
import com.site.reon.global.common.constant.Result;
import com.site.reon.global.common.constant.SessionConst;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.dto.SessionMember;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Controller
@RequestMapping("/login/email")
@RequiredArgsConstructor
public class MemberEmailLoginController {
    private final MemberService memberService;
    private final MemberLoginService memberLoginService;
    private final HttpSession httpSession;

    @PostMapping("/sign-up")
    public ResponseEntity signup(@Valid @RequestBody SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(allErrors)) {
                return BasicResponse.clientError(allErrors.get(0).getDefaultMessage());
            }
            return BasicResponse.clientError(Result.FAIL.message());
        }

        try {
            memberLoginService.signup(signUpDto);
            return ResponseEntity.ok(SUCCESS);
        } catch (DuplicateMemberException e) {
            return BasicResponse.clientError(e.getMessage());
        } catch (Exception e) {
            return BasicResponse.internalServerError("회원가입을 실패하였습니다. 다시 시도해 주세요.");
        }
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        memberLoginService.emailAuthenticate(loginDto);

        Member member = memberService.getMemberWithAuthorities(loginDto.getEmail(), OAuth2Client.EMPTY);
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, SessionMember.from(member));
        httpSession.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, loginDto.getEmail());

        return ResponseEntity.ok(MemberDto.from(member));
    }
}