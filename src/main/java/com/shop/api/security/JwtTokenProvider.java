package com.shop.api.security;

import com.shop.api.security.dto.JwtTokenDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private String accessSecretKey = "justDoItAccessKey";
    private String refreshSecretKey = "justDoItRefreshKey";

    private long accessTokenValidTime = 60 * 60 * 1000L;
    private long refreshTokenValidTime = 600 * 60 * 1000L;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    public JwtTokenDTO createAccessToken(String userPk, List<String> roles ) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        //Refresh Token
        String refreshToken =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();

        return JwtTokenDTO.builder().accessToken(accessToken).refreshToken(refreshToken).tokenKey(userPk).build();

    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
    }


    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


    public boolean validateToken(String jwtToken, HttpServletRequest request) {
        String errMsg = "errMsg";

        try {
            if(jwtToken.equals("") || jwtToken == null){
                throw new NullPointerException();
            }
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (UnsupportedJwtException e) {
            request.setAttribute(errMsg, "TE100");
            return false;
        } catch (MalformedJwtException e) {
            request.setAttribute(errMsg, "TE101");
            return false;
        } catch (ExpiredJwtException e) {
            request.setAttribute(errMsg, "TE102");
            return false;
        } catch (SignatureException e) {
            request.setAttribute(errMsg, "TE103");
            return false;
        } catch (IllegalArgumentException e) {
            request.setAttribute(errMsg, "TE104");
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public String validateRefreshToken(JwtTokenDTO JwtTokenDTO){

        String refreshToken = JwtTokenDTO.getRefreshToken();
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
            if (!claims.getBody().getExpiration().before(new Date())) {
                return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
            }
        }catch (Exception e) {
            return null;
        }
        return null;
    }

    public String recreationAccessToken(String userPk, Object roles){

        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        return accessToken;
    }



}
