package com.shop.api.domain.seller.contoller;

import com.shop.api.common.response.dto.NormalResDTO;
import com.shop.api.common.response.message.SellerResponseEnum;
import com.shop.api.domain.seller.dto.SellerDTO;
import com.shop.api.domain.seller.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping()
    public ResponseEntity<?> saveSeller(@RequestBody  @Valid SellerDTO sellerDTO){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        sellerDTO.setSellerMemberId(memberId);
        sellerService.saveSeller(sellerDTO);
        NormalResDTO normalResDTO = NormalResDTO.builder()
                .code(SellerResponseEnum.CREATE_SELLER.getCode())
                .httpStatus(SellerResponseEnum.CREATE_SELLER.getStatus())
                .message(SellerResponseEnum.CREATE_SELLER.getMessage())
                .build();
        return new ResponseEntity<>(normalResDTO, normalResDTO.getHttpStatus());
    }
}
