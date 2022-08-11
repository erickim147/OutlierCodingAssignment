package com.shop.api.domain.order.controller;

import com.shop.api.common.response.dto.NormalResDTO;
import com.shop.api.common.response.dto.ResultResDTO;
import com.shop.api.common.response.message.CartResponseEnum;
import com.shop.api.common.response.message.OrderResponseEnum;
import com.shop.api.domain.order.dto.OrderDTO;
import com.shop.api.domain.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody OrderDTO orderDTO){
        orderService.saveOrder(orderDTO);
        NormalResDTO normalResDTO = NormalResDTO.builder()
                .code(OrderResponseEnum.ORDER_SUCCESS.getCode())
                .httpStatus(OrderResponseEnum.ORDER_SUCCESS.getStatus())
                .message(OrderResponseEnum.ORDER_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(normalResDTO, normalResDTO.getHttpStatus());
    }

    @GetMapping
    public ResponseEntity<?> selectAll(){
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderDTO> resultList = orderService.selectAll(memberId);
        ResultResDTO resultResDTO = ResultResDTO.builder()
                .code(OrderResponseEnum.SUCCESS_READ_ORDER.getCode())
                .httpStatus(OrderResponseEnum.SUCCESS_READ_ORDER.getStatus())
                .message(OrderResponseEnum.SUCCESS_READ_ORDER.getMessage())
                .count(resultList.size())
                .data(resultList)
                .build();
        return new ResponseEntity<>(resultResDTO, resultResDTO.getHttpStatus());
    }

}
