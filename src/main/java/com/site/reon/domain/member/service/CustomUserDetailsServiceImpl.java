package com.site.reon.domain.member.service;

import com.site.reon.domain.member.entity.Member;
import com.site.reon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        return memberRepository.findOneWithAuthoritiesByEmail(email)
                .map(member -> createUser(email, member))
                .orElseThrow(() -> new UsernameNotFoundException(email + " : 회원 정보를 찾을 수 없습니다."));
    }

    private User createUser(String email, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(email + " : 비활성 계정입니다.");
        }

        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }
}