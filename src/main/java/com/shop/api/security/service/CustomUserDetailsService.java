package com.shop.api.security.service;

import com.shop.api.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        try {
            return memberService.selectOne(memberId);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}
