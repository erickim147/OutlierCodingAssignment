package com.shop.api.security.mapper;

import com.shop.api.security.dto.JwtTokenDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
    public interface JwtMapper {
    boolean existsByKeyMemberId(String keyMemberId);

    void deleteByKeyMemberId(String keyMemberId);

    void saveToken(JwtTokenDTO jwtTokenDTO);

    JwtTokenDTO findByRefreshToken(String refreshToken);
}
