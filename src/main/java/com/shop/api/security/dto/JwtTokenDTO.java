package com.shop.api.security.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtTokenDTO {

    private String accessToken;
    private String refreshToken;
    private String tokenKey;
}
