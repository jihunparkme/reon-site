package com.site.reon.domain.member.service;

import com.site.reon.domain.member.constant.Role;
import com.site.reon.domain.member.dto.MemberDto;
import com.site.reon.domain.member.dto.SignUpDto;
import com.site.reon.domain.member.entity.Authority;
import com.site.reon.domain.member.entity.Member;
import com.site.reon.domain.member.repository.MemberRepository;
import com.site.reon.global.security.exception.DuplicateMemberException;
import com.site.reon.global.security.exception.NotFoundMemberException;
import com.site.reon.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository MemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signup(SignUpDto signUpDto) {
        Member findMember = MemberRepository.findOneWithAuthoritiesByEmail(signUpDto.getEmail()).orElse(null);
        if (findMember != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 이메일입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName(Role.USER.key())
                .build();

        Member member = Member.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        MemberRepository.save(member);
    }

    /**
     * E-mail 기준으로 회원 조회
     */
    @Override
    @Transactional(readOnly = true)
    public MemberDto getMemberWithAuthorities(String email) {
        return MemberDto.from(MemberRepository.findOneWithAuthoritiesByEmail(email).orElse(null));
    }

    /**
     * SecurityContext 에 저장된 기준으로 회원 조회
     */
    @Override
    @Transactional(readOnly = true)
    public MemberDto getMyMemberWithAuthorities() {
        return MemberDto.from(
                SecurityUtil.getCurrentEmail()
                        .flatMap(MemberRepository::findOneWithAuthoritiesByEmail)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}
