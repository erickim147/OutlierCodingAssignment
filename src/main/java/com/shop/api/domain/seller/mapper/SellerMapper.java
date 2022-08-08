package com.shop.api.domain.seller.mapper;

import com.shop.api.domain.seller.dto.SellerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SellerMapper {

    void saveSeller(SellerDTO sellerDTO);

    int selectCount(String memberId);
}
