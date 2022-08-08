package com.shop.api.domain.order.dto;

import com.shop.api.domain.product.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private long orderId;
    private long productId;
    private String memberId;
    private long cartId;
    private long totalPrice;
    private long totalAmount;
    private String orderCreateTime;
    private ProductDTO productInfo;

}
