package com.shop.api.domain.member.service;

import com.shop.api.domain.member.dto.RoleDTO;
import com.shop.api.domain.member.mapper.MemberMapper;
import com.shop.api.domain.member.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void joinMember(MemberDTO memberDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(memberDTO.getMemberPw());
            memberDTO.setMemberPw(encodedPassword);
            memberMapper.joinMember(memberDTO);
            RoleDTO roleDTO = RoleDTO.builder()
                    .roleMemberId(memberDTO.getMemberId())
                    .role("ROLE_USER")
                    .build();
            memberMapper.insertRole(roleDTO);

        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("UE101");
        }
    }

    public List<MemberDTO> selectAll() {
        return memberMapper.selectAll();
    }

    @Transactional
    public MemberDTO selectOne(String memberId) {
        MemberDTO memberDTO = memberMapper.selectOne(memberId);

        memberDTO.setMemberRoles(memberMapper.selectRoles(memberId));
        return memberDTO;
    }

    public MemberDTO loginMember(String memberId) {
        return memberMapper.loginMember(memberId);
    }

}
