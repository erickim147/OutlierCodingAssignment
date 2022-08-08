package com.shop.api.domain.cart.controller;

import com.shop.api.common.response.dto.NormalResDTO;
import com.shop.api.common.response.dto.ResultResDTO;
import com.shop.api.common.response.message.CartResponseEnum;
import com.shop.api.domain.cart.dto.CartDTO;
import com.shop.api.domain.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v5/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<?> saveCart(@RequestBody @Valid CartDTO cartDTO){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        cartDTO.setMemberId(memberId);
        cartService.saveCart(cartDTO);
        NormalResDTO normalResDTO = NormalResDTO.builder()
                .code(CartResponseEnum.CREATE_CART.getCode())
                .httpStatus(CartResponseEnum.CREATE_CART.getStatus())
                .message(CartResponseEnum.CREATE_CART.getMessage())
                .build();
        return new ResponseEntity<>(normalResDTO, normalResDTO.getHttpStatus());
    }

    @GetMapping()
    public ResponseEntity<?> selectAll() {
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<CartDTO> returnList = cartService.selectAll(memberId);

        ResultResDTO resultResDTO = ResultResDTO.builder()
                .code(CartResponseEnum.READ_ALL_CART.getCode())
                .httpStatus(CartResponseEnum.CREATE_CART.getStatus())
                .message(CartResponseEnum.READ_ALL_CART.getMessage())
                .count(returnList.size())
                .data(returnList)
                .build();
        return new ResponseEntity<>(resultResDTO, resultResDTO.getHttpStatus());
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteCart(@RequestParam long productId) {
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.deleteCart(productId, memberId);

        NormalResDTO normalResDTO = NormalResDTO.builder()
                .code(CartResponseEnum.DELETE_CART_SUCCESS.getCode())
                .httpStatus(CartResponseEnum.DELETE_CART_SUCCESS.getStatus())
                .message(CartResponseEnum.DELETE_CART_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(normalResDTO, normalResDTO.getHttpStatus());
    }

    @DeleteMapping("/del")
    public ResponseEntity<?> deleteAll() {
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.deleteAll(memberId);

        NormalResDTO normalResDTO = NormalResDTO.builder()
                .code(CartResponseEnum.DELETE_ALL_CART_SUCCESS.getCode())
                .httpStatus(CartResponseEnum.DELETE_ALL_CART_SUCCESS.getStatus())
                .message(CartResponseEnum.DELETE_ALL_CART_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(normalResDTO, normalResDTO.getHttpStatus());
    }
}
