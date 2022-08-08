package com.shop.api.security.service;

import com.shop.api.common.response.message.TokenResponseEnum;
import com.shop.api.security.JwtTokenProvider;
import com.shop.api.security.dto.JwtTokenDTO;
import com.shop.api.security.mapper.JwtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JwtMapper jwtMapper;

    @Transactional
    public void login(JwtTokenDTO jwtTokenDTO){

        if(jwtMapper.existsByKeyMemberId(jwtTokenDTO.getTokenKey())){
            jwtMapper.deleteByKeyMemberId(jwtTokenDTO.getTokenKey());
        }
        jwtMapper.saveToken(jwtTokenDTO);
    }

    public Map<String, String> validateRefreshToken(String refreshToken){
        JwtTokenDTO jwtTokenDTO = jwtMapper.findByRefreshToken(refreshToken);

        if(jwtTokenDTO == null) {
            Map<String, String> map = new HashMap<>();
            map.put("code", TokenResponseEnum.REFRESH_TOKEN_EXCEPTION.getCode());
            map.put("message", TokenResponseEnum.REFRESH_TOKEN_EXCEPTION.getMessage());
            return map;
        }

        String newAccessToken = jwtTokenProvider.validateRefreshToken(jwtTokenDTO);

        return createRefreshJson(newAccessToken);
    }

    public Map<String, String> createRefreshJson(String newAccessToken){

        Map<String, String> map = new HashMap<>();
        if(newAccessToken == null){
            map.put("code", TokenResponseEnum.REFRESH_TOKEN_EXCEPTION.getCode());
            map.put("message", TokenResponseEnum.REFRESH_TOKEN_EXCEPTION.getMessage());
            return map;
        }

        map.put("code", TokenResponseEnum.CREATE_REFRESH_TOKEN.getCode());
        map.put("message", TokenResponseEnum.CREATE_REFRESH_TOKEN.getMessage());
        map.put("accessToken", newAccessToken);

        return map;


    }
}
