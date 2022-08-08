package com.shop.api.domain.product.controller;

import com.shop.api.common.response.dto.NormalResDTO;
import com.shop.api.common.response.message.ProductResponseEnum;
import com.shop.api.domain.product.dto.ProductDTO;
import com.shop.api.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v3/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductDTO productDTO){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        productDTO.setPdSellerId(sellerId);
        productService.saveProduct(productDTO);
        NormalResDTO normalResDTO = NormalResDTO.builder()
                .code(ProductResponseEnum.CREATE_PRODUCT.getCode())
                .httpStatus(ProductResponseEnum.CREATE_PRODUCT.getStatus())
                .message(ProductResponseEnum.CREATE_PRODUCT.getMessage())
                .build();
        return new ResponseEntity<>(normalResDTO, normalResDTO.getHttpStatus());
    }

}
