package com.shop.api.security.controller;

import com.shop.api.common.response.dto.ErrorResDTO;
import com.shop.api.common.response.dto.ResultResDTO;
import com.shop.api.common.response.message.TokenResponseEnum;
import com.shop.api.security.dto.JwtTokenDTO;
import com.shop.api.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/api/v1/refresh")
    public ResponseEntity<?> validateRefreshToken(@RequestBody JwtTokenDTO jwtTokenDTO){

        Map<String, String> map = jwtService.validateRefreshToken(jwtTokenDTO.getRefreshToken());
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("accessToken", map.get("accessToken"));

        if(map.get("code").equals("TE106")){

            ErrorResDTO errorResDTO = ErrorResDTO.builder()
                    .errorCode(map.get("code"))
                    .httpStatus(TokenResponseEnum.REFRESH_TOKEN_EXCEPTION.getStatus())
                    .errorMessage(map.get("message"))
                    .build();

            return new ResponseEntity<>(errorResDTO, errorResDTO.getHttpStatus());
        }

        ResultResDTO resultResDTO = ResultResDTO.builder()
                .code(map.get("code"))
                .httpStatus(TokenResponseEnum.CREATE_REFRESH_TOKEN.getStatus())
                .message(map.get("message"))
                .count(1)
                .data(resultMap)
                .build();

        return new ResponseEntity<>(resultResDTO, resultResDTO.getHttpStatus());

    }
}
