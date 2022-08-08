package com.shop.api.domain.member.mapper;

import com.shop.api.domain.member.dto.MemberDTO;
import com.shop.api.domain.member.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemberMapper {
     void joinMember(MemberDTO memberDTO);
     void insertRole(RoleDTO roleDTO);
     List<MemberDTO> selectAll();

     MemberDTO selectOne(String memberId);

     List<String> selectRoles(String memberId);

     MemberDTO loginMember(String memberId);
}
