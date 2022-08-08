package com.shop.api.domain.seller.service;

import com.shop.api.domain.member.dto.RoleDTO;
import com.shop.api.domain.member.mapper.MemberMapper;
import com.shop.api.domain.seller.dto.SellerDTO;
import com.shop.api.domain.seller.mapper.SellerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerService {

    @Autowired
    SellerMapper sellerMapper;
    @Autowired
    MemberMapper memberMapper;

    @Transactional
    public void saveSeller(SellerDTO sellerDTO){
        System.out.println(sellerMapper.selectCount(sellerDTO.getSellerMemberId()));
            if(sellerMapper.selectCount(sellerDTO.getSellerMemberId()) == 1){
                throw new IllegalArgumentException("SCE103");
            } else {
                sellerMapper.saveSeller(sellerDTO);
                RoleDTO roleDTO = RoleDTO.builder()
                        .roleMemberId(sellerDTO.getSellerMemberId())
                        .role("ROLE_SELLER")
                        .build();
                memberMapper.insertRole(roleDTO);
            }
    }
}
