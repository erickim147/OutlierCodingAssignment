package com.shop.api.domain.member.controller;

import com.shop.api.common.response.dto.NormalResDTO;
import com.shop.api.common.response.dto.ResultResDTO;
import com.shop.api.common.response.message.ResponseEnum;
import com.shop.api.domain.member.dto.MemberDTO;
import com.shop.api.domain.member.service.MemberService;
import com.shop.api.security.JwtTokenProvider;
import com.shop.api.security.dto.JwtTokenDTO;
import com.shop.api.security.service.JwtService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;

    /*
     * 회원가입 (permitAll)
     * */
    @PostMapping("/user")
    public ResponseEntity<?> joinMember(@RequestBody @Valid MemberDTO memberDTO) throws NotFoundException {

        memberService.joinMember(memberDTO);
        NormalResDTO normalResDTO = NormalResDTO.builder()
                .code(ResponseEnum.CREATED_USER.getCode())
                .httpStatus(ResponseEnum.CREATED_USER.getStatus())
                .message(String.valueOf(ResponseEnum.CREATED_USER.getMessage()))
                .build();
        return new ResponseEntity<>(normalResDTO, normalResDTO.getHttpStatus());

    }


    @GetMapping("/user")
    public ResponseEntity<?> selectOne(@RequestParam String memberId) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(memberId)) {
            throw new IllegalArgumentException("AE100");
        }

        Map<String, String> resultMap = new HashMap<>();
        MemberDTO memberDTO = memberService.selectOne(memberId);

        resultMap.put("memberId", memberDTO.getMemberId());
        resultMap.put("memberNm", memberDTO.getMemberNm());
        resultMap.put("memberPhoneNum", memberDTO.getMemberPhoneNum());
        resultMap.put("memberEmail", memberDTO.getMemberEmail());
        resultMap.put("memberAddr", memberDTO.getMemberAddr());
        resultMap.put("memberBirth", memberDTO.getMemberBirth());

        ResultResDTO resultResDTO = ResultResDTO.builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .httpStatus(HttpStatus.OK)
                .message(String.valueOf(ResponseEnum.READ_USER.getMessage()))
                .count(1)
                .data(resultMap)
                .build();
        return new ResponseEntity<>(resultResDTO, resultResDTO.getHttpStatus());
    }

    /*
     * 회원 전체 조회 (hasRole[ROLE_ADMIN])
     * */
    @GetMapping("/users")
    public ResponseEntity<?> selectAll() {
        List<Map<String, String>> resultList = new ArrayList<>();
        List<MemberDTO> memberDTO = memberService.selectAll();

        for (MemberDTO list : memberDTO) {
            Map<String, String> member = new HashMap<>();
            member.put("memberId", list.getMemberId());
            member.put("memberNm", list.getMemberNm());
            member.put("memberPhoneNum", list.getMemberPhoneNum());
            member.put("memberEmail", list.getMemberEmail());
            member.put("memberAddr", list.getMemberAddr());
            member.put("memberBirth", list.getMemberBirth());
            resultList.add(member);
        }

        ResultResDTO resultResDTO = ResultResDTO.builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .httpStatus(HttpStatus.OK)
                .message(String.valueOf(ResponseEnum.READ_ALL_USER.getMessage()))
                .count(memberDTO.size())
                .data(resultList)
                .build();
        return new ResponseEntity<>(resultResDTO, resultResDTO.getHttpStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String memberId, @RequestParam String memberPw) {
        //log.info("user email = {}", user.get("email"));
        try {
            Map<String, String> resultMap = new HashMap<>();
            MemberDTO member = memberService.loginMember(memberId);
            if (!passwordEncoder.matches(memberPw, member.getMemberPw())) {
                throw new IllegalArgumentException("NOT_MATCH_PASSWORD");
            } else {
                JwtTokenDTO jwtTokenDTO = jwtTokenProvider.createAccessToken(member.getUsername(), member.getMemberRoles());
                jwtService.login(jwtTokenDTO);

                resultMap.put("memberId", member.getMemberId());
                resultMap.put("accessToken", jwtTokenDTO.getAccessToken());
                resultMap.put("refreshToken", jwtTokenDTO.getRefreshToken());

                ResultResDTO resultResDTO = ResultResDTO.builder()
                        .code(ResponseEnum.LOGIN_SUCCESS.getCode())
                        .httpStatus(ResponseEnum.LOGIN_SUCCESS.getStatus())
                        .message(ResponseEnum.LOGIN_SUCCESS.getMessage())
                        .count(resultMap.size())
                        .data(resultMap)
                        .build();
                return new ResponseEntity<>(resultResDTO, resultResDTO.getHttpStatus());
            }

        } catch (NullPointerException e) {
            throw new NullPointerException("NOT_FOUND_USER_ID");
        }
    }
}
